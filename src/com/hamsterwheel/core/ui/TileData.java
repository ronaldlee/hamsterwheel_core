package com.hamsterwheel.core.ui;

public class TileData {
	private String id;
	private int type;
	private int grid_x;
	private int grid_y;
	
	public TileData(String id,int type, int grid_x, int grid_y) {
		this.id = id;
		this.type = type;
		this.grid_x = grid_x;
		this.grid_y = grid_y;
	}
	
	public String getId() {
		return id;
	}
	
	public int getType() {
		return type;
	}
	
	public int getGridX() {
		return grid_x;
	}
	public int getGridY() {
		return grid_y;
	}
	public void setId(String id) {
		this.id = id;
	}
}