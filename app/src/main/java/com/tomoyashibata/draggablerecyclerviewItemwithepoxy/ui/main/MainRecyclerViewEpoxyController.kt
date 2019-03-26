package com.tomoyashibata.draggablerecyclerviewItemwithepoxy.ui.main

import android.view.MotionEvent
import com.airbnb.epoxy.EpoxyController
import com.tomoyashibata.draggablerecyclerviewItemwithepoxy.data.Item
import com.tomoyashibata.draggablerecyclerviewItemwithepoxy.recyclerItem
import com.tomoyashibata.draggablerecyclerviewItemwithepoxy.ui.helper.SingleLiveEvent


class MainRecyclerViewEpoxyController : EpoxyController() {
  val clickDragHandleEvent: SingleLiveEvent<Long> = SingleLiveEvent()
  val items = (0..30).map {
    Item(
      id = it.toLong(),
      name = "Item $it"
    )
  }.toMutableList()

  override fun buildModels() {
    this.items.forEach {
      recyclerItem {
        id(it.id)
        item(it)
        onTouchItemDragHandle { _, event ->
          if (event.actionMasked == MotionEvent.ACTION_DOWN) {
            this@MainRecyclerViewEpoxyController.clickDragHandleEvent.value = (it.id)
          }
          return@onTouchItemDragHandle true
        }
      }
    }
  }
}
