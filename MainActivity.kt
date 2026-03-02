package com.example.mydicerollerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)





        val rollButton : Button = findViewById(R.id.roll_btn)
       rollButton.setOnClickListener {
           rollDice()
            Toast.makeText(this, "Button Clicked", Toast.LENGTH_SHORT).show()
        }



    }

    private fun rollDice() {

        val drawableResource=when(java.util.Random().nextInt(6)+1){
            1->R.drawable.dice_1
            2->R.drawable.dice_2
            3->R.drawable.dice_3
            4->R.drawable.dice_4
            5->R.drawable.dice_5
            else->R.drawable.dice_6
        }

        val diceImage:ImageView=findViewById(R.id.diceImage)
        diceImage.setImageResource(drawableResource)


    }
}