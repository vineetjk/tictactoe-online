package code.vineet.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.contentcapture.ContentCaptureSessionId
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.lang.Exception
import java.util.*


import kotlin.collections.ArrayList
import kotlin.collections.HashMap

open class MainActivity : AppCompatActivity() {

    //databse instance
    private var database = FirebaseDatabase.getInstance()
    private var myRef = database.reference

    var myEmail:String?=null

    private var mFirebaseAnalytics:FirebaseAnalytics?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)

        var b: Bundle? = intent.extras
        myEmail = b!!.getString("email")
        Incommingcalls()

    }

    fun btClick( view:View){

        val btSelected = view as Button

        var cellId = 0

        when(btSelected.id)
        {
            R.id.bt1 -> cellId = 1
            R.id.bt2 -> cellId = 2
            R.id.bt3 -> cellId = 3
            R.id.bt4 -> cellId = 4
            R.id.bt5 -> cellId = 5
            R.id.bt6 -> cellId = 6
            R.id.bt7 -> cellId = 7
            R.id.bt8 -> cellId = 8
            R.id.bt9 -> cellId = 9
        }

        //Toast.makeText(this, "hey you pressed $cellId", Toast.LENGTH_SHORT).show()

       // playGame(cellId,btSelected)
        // for ofline remove comment

        myRef.child("PlayerOnline").child(sessionId!!).child(cellId.toString()).setValue(myEmail)

    }

    var activePlayer = 1
    var player1 = ArrayList<Int> ()
    var player2 = ArrayList<Int> ()
    var scoreX = 0
    var scoreO = 0


    fun playGame(cellId:Int, btSelected:Button){

        if(activePlayer == 1){

            btSelected.text = "X"
            btSelected.setBackgroundResource(R.color.red)

            player1.add(cellId)
            activePlayer = 2

        }else{

            btSelected.text = "O"
            player2.add(cellId)
            btSelected.setBackgroundResource(R.color.green)
            activePlayer = 1


        }

        btSelected.isEnabled = false

        checkWinner()


    }



    fun checkWinner(){

        var winner = 0


        //row1
        if(player1.contains(1) && player1.contains(2) && player1.contains(3) ){

            winner = 1

        }

        if(player2.contains(1) && player2.contains(2) && player2.contains(3) ){

            winner = 2

        }


        //row2
        if(player1.contains(4) && player1.contains(5) && player1.contains(6) ){

            winner = 1

        }
        if(player2.contains(4) && player2.contains(5) && player2.contains(6) ){

            winner = 2

        }


        //row3
        if(player1.contains(7) && player1.contains(8) && player1.contains(9) ){

            winner = 1

        }

        if(player2.contains(7) && player2.contains(8) && player2.contains(9) ){

            winner = 2

        }

        //column1
        if(player1.contains(1) && player1.contains(4) && player1.contains(7) ){

            winner = 1

        }

        if(player2.contains(1) && player2.contains(4) && player2.contains(7) ){

            winner = 2

        }


        //column2
        if(player1.contains(2) && player1.contains(5) && player1.contains(8) ){

            winner = 1

        }
        if(player2.contains(2) && player2.contains(5) && player2.contains(8) ){

            winner = 2

        }



        //column3
        if(player1.contains(3) && player1.contains(6) && player1.contains(9) ){

            winner = 1

        }

        if(player2.contains(3) && player2.contains(6) && player2.contains(9) ){

            winner = 2

        }

        if(winner == 1 || winner == 2){


            if(winner == 1) {
                Toast.makeText(this, "hey winner is : Player 1", Toast.LENGTH_SHORT).show()
                scoreX++
                tvplayerX.text = "Player X : $scoreX"



            }
            else if(winner == 2){
                Toast.makeText(this,"the winner is Player 2 ", Toast.LENGTH_SHORT).show()
                scoreO++
                tvplayerO.text = "Player O : $scoreO"
            }
            bt1.isEnabled = false
            bt2.isEnabled = false
            bt3.isEnabled = false
            bt4.isEnabled = false
            bt5.isEnabled = false
            bt6.isEnabled = false
            bt7.isEnabled = false
            bt8.isEnabled = false
            bt9.isEnabled = false




        }





    }

    fun btClickAgain(view: View) {
        clearAll()
        scoreO = 0
        scoreX = 0
        tvplayerX.text = "Player X : $scoreX"
        tvplayerO.text = "Player O : $scoreO"

 }



    fun btClickRst(view: View){

        clearAll()


    }
    fun clearAll(){
        player1.clear()
        player2.clear()

        bt1.isEnabled = true
        bt2.isEnabled = true
        bt3.isEnabled = true
        bt4.isEnabled = true
        bt5.isEnabled = true
        bt6.isEnabled = true
        bt7.isEnabled = true
        bt8.isEnabled = true
        bt9.isEnabled = true

        bt1.text = ""
        bt2.text = ""
        bt3.text = ""
        bt4.text = ""
        bt5.text = ""
        bt6.text = ""
        bt7.text = ""
        bt8.text = ""
        bt9.text = ""

        bt1.setBackgroundResource(R.color.whileBu)
        bt2.setBackgroundResource(R.color.whileBu)
        bt3.setBackgroundResource(R.color.whileBu)
        bt4.setBackgroundResource(R.color.whileBu)
        bt5.setBackgroundResource(R.color.whileBu)
        bt6.setBackgroundResource(R.color.whileBu)
        bt7.setBackgroundResource(R.color.whileBu)
        bt8.setBackgroundResource(R.color.whileBu)
        bt9.setBackgroundResource(R.color.whileBu)



    }


     fun btOnclickReq(view: View){

        var userEmail = etMail.text.toString()
        myRef.child("Users").child(SplitString(userEmail)).child("Request").push().setValue(myEmail)


        PlayerOnline(SplitString(myEmail!!)+SplitString(userEmail))
         PlayerSymbol = "X"

    }
     fun btOnclickAcc(view: android.view.View){
        var userEmail = etMail.text.toString()

         myRef.child("Users").child(SplitString(userEmail)).child("Request").push().setValue(myEmail)

         PlayerOnline(SplitString(userEmail)+SplitString(myEmail!!))
         PlayerSymbol = "O"

     }
var sessionId:String?=null
    var PlayerSymbol:String?=null
    fun PlayerOnline(sessionId:String){
    this.sessionId = sessionId

        myRef.child("PlayerOnline").child(sessionId)
            .addValueEventListener(object :ValueEventListener{

                override fun onCancelled(p0: DatabaseError) {



                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    try {

                        player1.clear()
                        player2.clear()
                        val td = dataSnapshot!!.value as HashMap<String,Any>

                        if (td!=null){

                            var value:String
                            for(key in td.keys) {
                                value = td[key] as String

                                if (value!=myEmail){
                                    activePlayer = if (PlayerSymbol === "X" ) 1 else 2

                                }else{
                                    activePlayer = if (PlayerSymbol === "X" ) 2 else 1

                                }
                                autoPlay(key.toInt())



                            }
                        }
                    }catch (ex:Exception){}

                }



            })

    }

    fun Incommingcalls(){
        myRef.child("Users").child(SplitString(myEmail!!)).child("Request")
            .addValueEventListener(object :ValueEventListener{

                override fun onCancelled(p0: DatabaseError) {



                     }

                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    try {
                        val td = dataSnapshot!!.value as HashMap<String,Any>

                        if (td!=null){

                            var value:String
                            for(key in td.keys) {
                                value = td[key] as String
                                etMail.setText(value)

                                myRef.child("Users").child(SplitString(myEmail!!)).child("Request").setValue(true)

                                break

                            }
                        }
                    }catch (ex:Exception){}

                     }



            })
    }

    fun SplitString(str:String): String {
        var split = str.split("@")
        return split[0]

    }

    fun autoPlay(cellId: Int){



        var buSelected:Button?
        buSelected =  when(cellId){
            1-> bt1
            2-> bt2
            3-> bt3
            4-> bt4
            5-> bt5
            6-> bt6
            7-> bt7
            8-> bt8
            9-> bt9
            else ->{ bt1}

        }

        playGame(cellId, buSelected)

    }

}