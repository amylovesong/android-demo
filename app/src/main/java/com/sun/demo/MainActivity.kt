package com.sun.demo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.TextView
import com.sun.demo.sensor.SensorActivity
import com.sun.demo.vibrate.VibrateActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 *
 * @author sxl (sunxiaoling@didiglobal.com)
 * @since 2020/9/28.
 */
class MainActivity: AppCompatActivity() {

    private val demoList:MutableList<ItemData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        fillData(demoList);
        list_demo.adapter = MyAdapter(this, demoList)
        list_demo.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            startTarget(view.tag as Class<out Activity>)
        }
    }

    private fun fillData(demoList: MutableList<ItemData>) {
        demoList.add(ItemData("Sensor", SensorActivity::class.java))
        demoList.add(ItemData("Vibrate", VibrateActivity::class.java))
    }

    private fun startTarget(clazz: Class<out Activity>) {
        startActivity(Intent(this, clazz))
    }

    private data class ItemData(val title: String, val targetActivityClazz: Class<out Activity>)

    private class MyAdapter(val context: Activity, val data: MutableList<ItemData>) : BaseAdapter() {

        override fun getCount(): Int {
            return data.size
        }

        override fun getItem(position: Int): Any {
            return data[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var itemView: TextView? = null
            if (convertView is TextView) {
                itemView = convertView
            }
            if (itemView == null) {
                itemView = TextView(context)
                itemView.setPadding(20, 40, 20, 40)
            }

            val itemData = data[position]
            itemView.let {
                it.text = itemData.title
                it.tag = itemData.targetActivityClazz
            }

            return itemView
        }

    }
}