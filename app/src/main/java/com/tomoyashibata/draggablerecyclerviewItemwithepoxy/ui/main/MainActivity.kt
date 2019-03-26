package com.tomoyashibata.draggablerecyclerviewItemwithepoxy.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.airbnb.epoxy.EpoxyTouchHelper
import com.tomoyashibata.draggablerecyclerviewItemwithepoxy.R
import com.tomoyashibata.draggablerecyclerviewItemwithepoxy.RecyclerItemBindingModel_
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    this.setupMainEpoxy()
  }

  private fun setupMainEpoxy() {
    val controller = MainRecyclerViewEpoxyController()
    this.main_recycler_view.adapter = controller.adapter
    controller.requestModelBuild()

    val dragCallback = object : EpoxyTouchHelper.DragCallbacks<RecyclerItemBindingModel_>() {
      override fun onModelMoved(
        fromPosition: Int,
        toPosition: Int,
        modelBeingMoved: RecyclerItemBindingModel_?,
        itemView: View?
      ) {
        val item = controller.items.removeAt(fromPosition)
        controller.items.add(toPosition, item)
      }

      override fun onDragStarted(
        model: RecyclerItemBindingModel_?,
        itemView: View?,
        adapterPosition: Int
      ) {
        itemView?.setBackgroundColor(this@MainActivity.getColor(R.color.colorPrimary))
      }

      override fun onDragReleased(model: RecyclerItemBindingModel_?, itemView: View?) {
        itemView?.setBackgroundColor(this@MainActivity.getColor(android.R.color.transparent))
      }

      override fun isDragEnabledForModel(model: RecyclerItemBindingModel_?): Boolean = true
    }

    val itemTouchHelper = EpoxyTouchHelper.initDragging(controller)
      .withRecyclerView(this.main_recycler_view)
      .forVerticalList()
      .withTarget(RecyclerItemBindingModel_::class.java)
      .andCallbacks(dragCallback)

    controller.clickDragHandleEvent.observe(this, Observer {
      this.main_recycler_view.findViewHolderForItemId(it)
        ?.let { viewHolder ->
          itemTouchHelper.startDrag(viewHolder)
        }
    })
  }
}
