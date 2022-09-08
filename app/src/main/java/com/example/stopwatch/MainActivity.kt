package com.example.stopwatch

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //ウィジェットを変数に
        val btnStart = findViewById<Button>(R.id.btnStart)
        val btnStop = findViewById<Button>(R.id.btnStop)
        val btnClear = findViewById<Button>(R.id.btnClear)
        val timerView = findViewById<TextView>(R.id.ShowTime)
        //初期条件
        btnStart.isEnabled = true
        btnStop.isEnabled = false
        btnClear.isEnabled = false
        val timerZero = "00:00:0"
        timerView.text = timerZero

        val TERM_MILLISECOND: Long = 100
        var t = 0L          //カウント値を格納する変数
        val dataFormat = SimpleDateFormat("mm:ss:S", Locale.getDefault())
        //別のスレッドでの処理
        val handler = Handler(Looper.getMainLooper())
        val timer = object : Runnable {
            override fun run() {
                t += TERM_MILLISECOND       //tに+0.1（秒）をする
                timerView.text = dataFormat.format(t)               //カウント値を表示する
                handler.postDelayed(this, TERM_MILLISECOND)      //0.1秒後にまた呼び出される
            }
        }
        //スタートボタンが押されたら
        btnStart.setOnClickListener {
            handler.post(timer)
            btnStart.isEnabled = false
            btnStop.isEnabled = true
            btnClear.isEnabled = false
        }
        //ストップボタンが押されたら
        btnStop.setOnClickListener {
            handler.removeCallbacks(timer)
            btnStart.isEnabled = true
            btnStop.isEnabled = false
            btnClear.isEnabled =true
        }
        //Clearボタンが押されたら
        btnClear.setOnClickListener {
            handler.removeCallbacks(timer)
            t = 0L
            timerView.text = dataFormat.format(t)
            btnStart.isEnabled = true
            btnStop.isEnabled = false
            btnClear.isEnabled = false
        }
    }
}