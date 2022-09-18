package com.example.mytoproject.board

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.mytoproject.R
import com.example.mytoproject.utils.FBAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*

class BoardListLVAdapter(val boardList:MutableList<BoardModel>): BaseAdapter() {
    private val database by lazy { FirebaseDatabase.getInstance() }
    private val userRef = database.getReference("board")
    override fun getCount(): Int {
        return boardList.size
    }

    override fun getItem(p0: Int): Any {
        return boardList[p0]
    }

    override fun getItemId(p0: Int): Long {
return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

        var view = p1
       // if (view ==null) {

            view = LayoutInflater.from(p2?.context).inflate(R.layout.board_list_item,p2,false)


        //}

        val itemLinearLayoutview = view?.findViewById<LinearLayout>(R.id.itemview)


        val title = view?.findViewById<TextView>(R.id.titleArea)
        title!!.text = boardList[p0].title

        val content = view?.findViewById<TextView>(R.id.contentArea)
        content!!.text = boardList[p0].content

        val time = view?.findViewById<TextView>(R.id.timeArea)
        time!!.text=boardList[p0].time

        val timestamp = view?.findViewById<ImageView>(R.id.BadgeIV)




//        if(boardList[p0].uid.equals(FBAuth.getUid())) {
//            itemLinearLayoutview?.setBackgroundColor(Color.parseColor("@values/mainColor"))
//        }



            if(get_timestmap().toInt() <= 1 ) {
                    timestamp!!.visibility=View.VISIBLE
            }

            return view!!

    }

    private fun get_timestmap():Long {
        val currentTime = System.currentTimeMillis()

        val timestamps = userRef.child("timestamp").toString()

        val days = getIgnoredTimeDays(currentTime) - getIgnoredTimeDays(timestamps.toLong())

        val fewday = days / (24*60*60*1000)






        return fewday

    }



    private fun getIgnoredTimeDays(time:Long):Long {
        return Calendar.getInstance().apply {
            timeInMillis=time

            set(Calendar.HOUR_OF_DAY,0)
            set(Calendar.MINUTE,0)
            set(Calendar.SECOND,0)
            set(Calendar.MILLISECOND,0)

        }.timeInMillis
    }



}