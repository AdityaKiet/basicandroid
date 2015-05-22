package com.canteen.global;

import java.util.ArrayList;

import android.app.Application;
import android.graphics.Bitmap;

import com.canteen.dto.ItemDTO;
import com.canteen.dto.OrderDTO;

public class AllClassItems extends Application {

	private ArrayList<ItemDTO> itemList;
	private ArrayList<Bitmap> images;
	private OrderDTO order;

	public OrderDTO getOrder() {
		return order;
	}

	public void setOrder(OrderDTO order) {
		this.order = order;
	}

	public ArrayList<ItemDTO> getItemList() {
		return itemList;
	}

	public void setItemList(ArrayList<ItemDTO> itemList) {
		this.itemList = itemList;
	}

	public ArrayList<Bitmap> getImages() {
		return images;
	}

	public void setImages(ArrayList<Bitmap> images) {
		this.images = images;
	}

}