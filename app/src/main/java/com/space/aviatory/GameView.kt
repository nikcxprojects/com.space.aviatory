package com.space.aviatory

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.Paint
import android.media.MediaPlayer
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.lang.Exception
import java.util.*
import kotlin.math.abs

class GameView(ctx: Context, attributeSet: AttributeSet): SurfaceView(ctx,attributeSet) {

    var rocket = BitmapFactory.decodeResource(ctx.resources,R.mipmap.rocket_foreground)
    var fire = BitmapFactory.decodeResource(ctx.resources,R.mipmap.fire_foreground)
    var bg = BitmapFactory.decodeResource(ctx.resources,R.drawable.bg)
    var star = BitmapFactory.decodeResource(ctx.resources,R.mipmap.star_foreground)

    public var score = 0
    private val random = Random()
    private var y = 0
    private var dy = 0
    private val offset = 20
    private var millis = 0
    private var listener: EndListener? = null
    private var gx = 0
    private var gy = 0
    private var my = 0
    private var mx = 0
    private var paintT: Paint = Paint().apply {
        textSize = 80f
        color = Color.WHITE
    }
    private val player = MediaPlayer.create(ctx,R.raw.bg)
    private var paintB: Paint = Paint(Paint.DITHER_FLAG)

    init {
        player.setOnCompletionListener {
            player.start()
        }
        star = Bitmap.createScaledBitmap(star,star.width/3,star.height/3,true)
        holder.addCallback(object : SurfaceHolder.Callback{
            override fun surfaceCreated(holder: SurfaceHolder) {

            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
                val canvas = holder.lockCanvas()
                if(canvas!=null) {
                    y = canvas.width/2
                    gy = -fire.height
                    gx = random.nextInt(300)+canvas.width/2
                    my = -star.height
                    mx = random.nextInt(300)+canvas.width/2
                    player.start()
                    draw(canvas)
                    holder.unlockCanvasAndPost(canvas)
                }
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                player.stop()
            }

        })
        val updateThread = Thread {
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    if (!paused) {
                        update.run()
                        millis ++
                    }
                }
            }, 500, 16)
        }

        updateThread.start()
    }
    var code = -1f
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event!!.action) {
            MotionEvent.ACTION_UP -> {
                code = -1f
            }
            MotionEvent.ACTION_DOWN -> {
                code = event.x
            }
        }
        postInvalidate()
        return true
    }

    var paused = false
    var delta = 8
    val update = Runnable{
        var isEnd = false
        try {
            val canvas = holder.lockCanvas()
            if(gx<0) {
                gx = canvas.width
                gy = random.nextInt(300)+canvas.height/2
            }
            if(code>=0) {
                if(code>y) y+=delta
                else y-=delta
            }
            if(y<=-rocket.width) y = canvas.width
            if(y>=rocket.width+canvas.width) y = 0
            gy+=4
            my+=4
            if(gy>canvas.height) {
                gy = -fire.height
                gx = random.nextInt(300)+canvas.width/2
            }
            if(my>canvas.height) {
                my = -star.height
                mx = random.nextInt(300)+canvas.width/2
            }
            if(abs(gx-y)<=rocket.width/2 && gy>=canvas.height-rocket.height) {
                isEnd = true
            }
            if(abs(mx-y)<=rocket.width/2 && abs(my-canvas.height+rocket.height.toFloat())<=rocket.height/2) {
              score++
                my = 0
                mx = random.nextInt(300)+canvas.width/2
            }
            canvas.drawBitmap(bg,0f,0f,paintB)
            canvas.drawText("$score",canvas.width-50f,100f,paintT)
            canvas.drawBitmap(star,canvas.width-230f,20f,paintB)
            canvas.drawBitmap(rocket,y.toFloat(),canvas.height-rocket.height.toFloat(),paintB)
            canvas.drawBitmap(fire,gx.toFloat(),gy.toFloat(),paintB)
            canvas.drawBitmap(star,mx.toFloat(),my.toFloat(),paintB)
            holder.unlockCanvasAndPost(canvas)
           // Log.d("TAG","$isEnd")
            if(isEnd) {
                Log.d("TAG","END")
                togglePause()
                if(listener!=null) listener!!.end()

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setEndListener(list: EndListener) {
        this.listener = list
    }
    fun togglePause() {
        paused = !paused
    }
    companion object {
        interface EndListener {
            fun end();
        }
    }

}