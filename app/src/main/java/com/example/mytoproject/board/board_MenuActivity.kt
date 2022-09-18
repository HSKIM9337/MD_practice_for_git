package com.example.mytoproject.board

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.mytoproject.R
import com.example.mytoproject.databinding.ActivityBoardMenuBinding
import com.example.mytoproject.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.*


class board_MenuActivity : AppCompatActivity() {
    private lateinit var binding : ActivityBoardMenuBinding
    private val TAG = board_MenuActivity::class.java.simpleName

    private val boardDataList = mutableListOf<BoardModel>()
    private val boardKeyList = mutableListOf<String>() //파이어베이스의 데이터들의 키 값을 전달받을 변수


    private lateinit var boardRVadapter : BoardListLVAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_board_menu)


        boardRVadapter = BoardListLVAdapter(boardDataList)



        binding.boardListView.setOnItemClickListener { adapterView, view, i, l ->

//          1 )Listview에 있는 데이터 title content time 다른 액티비티로 전달해줘서 만들기
//            val intent = Intent(this,BoardInsideActivity::class.java)
//            intent.putExtra("title",boardDataList[i].title)
//            intent.putExtra("content",boardDataList[i].content)
//            intent.putExtra("time",boardDataList[i].time)
//            startActivity(intent)

            //2)파이어베이스에 있는 데이터의 id를 기반으로 다시 데이터를 받아오는 방법법
           val intent = Intent(this,BoardInsideActivity::class.java)
            intent.putExtra("key",boardKeyList[i])
            startActivity(intent)



        }


        binding.boardListView.adapter = boardRVadapter

        binding.WriteIV.setOnClickListener {

            var intent = Intent(this,BoardWriteActivity::class.java)

            startActivity(intent)

        }
        getFBBoardData()
    }


    private fun getFBBoardData() {
     val postListener = object : ValueEventListener {
         override fun onDataChange(snapshot: DataSnapshot) {

             boardDataList.clear()
             for (dataModel in snapshot.children) {
                    Log.d(TAG,dataModel.toString())


                 val item = dataModel.getValue(BoardModel::class.java)
                 boardDataList.add(item!!)
                 boardKeyList.add(dataModel.key.toString()) //키값을 전달받는다.
             }
             boardKeyList.reverse() //파이어베이스 이용 시에 필요
             boardDataList.reverse()
             boardRVadapter.notifyDataSetChanged()

         }

         override fun onCancelled(error: DatabaseError) {
             Log.w(TAG,"loadPost:onCancelled",error.toException())
         }
     }
        FBRef.boardRef.addValueEventListener(postListener)
    }

}