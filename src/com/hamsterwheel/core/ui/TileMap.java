package com.hamsterwheel.core.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;


public class TileMap {
	public static final int ANCHOR_XY_BOTTOM_LEFT = 0;
	public static final int ANCHOR_XY_TOP_LEFT = 1;

	private Paint map_paint;
	private Canvas mapCanvas;
	private Bitmap mapBitmap;
	private float dx,dy;
	private float newx,newy;
	
	private float max_left_x,max_right_x,max_top_y,max_bottom_y;
	
	private float slow_down_speed;
	
	private TileData tiles[][];
	private int real_tile_width,real_tile_height;
	private int actual_width,actual_height;
	private int tile_width,tile_height,num_tiles_height;
	private TileSpriteFactory tsf;
	
	public TileMap(int tile_width,int tile_height,TileData tiles[][], TileSpriteFactory tsf, 
				   int num_of_tiles_to_display_per_row, int screen_width, int screen_height,
				   int slow_down_speed,
				   int anchor_xy) {
		this.tsf = tsf;
		this.tile_width = tile_width;
		this.tile_height = tile_height;
		
		this.tiles = tiles;
		map_paint=new Paint();
		
		int num_tiles_width = tiles[0].length;
		num_tiles_height = tiles.length;
		
		real_tile_width = screen_width/num_of_tiles_to_display_per_row;
		real_tile_height = real_tile_width;
	
//Log.e("KKKK", "KKKK :real_tile_width: " + real_tile_width +"; num_tiles_width:" + num_tiles_width);		
		actual_width = num_tiles_width*real_tile_width;
		actual_height = num_tiles_height*real_tile_height;
		
		mapBitmap = Bitmap.createBitmap(actual_width,actual_height,Bitmap.Config.ARGB_4444);
		mapCanvas = new Canvas(mapBitmap);
		
		for (int i=0; i < num_tiles_height; i++) {
			for (int j=0; j < tiles[i].length; j++) {
				String id = tiles[i][j].getId();
				int type = tiles[i][j].getType();
				
				Bitmap map_tiles_sprite = tsf.getSpriteBitmap(id);
				
				if (map_tiles_sprite != null) {
					mapCanvas.drawBitmap(map_tiles_sprite, 
							new Rect(0,type*tile_height,tile_width,type*tile_height+tile_height), 
							new Rect(j*real_tile_width,i*real_tile_height,j*real_tile_width+real_tile_width,i*real_tile_height+real_tile_height), 
							null);
				}
			}
		}
		
		if (anchor_xy == ANCHOR_XY_BOTTOM_LEFT) {
			newy=-num_tiles_height*real_tile_height+screen_height; //init x y anchor should be configurable
		}
		else if (anchor_xy == ANCHOR_XY_TOP_LEFT) {
			newy = 0;
		}
		
		max_left_x=0;
		max_right_x = -(real_tile_width*num_tiles_width-screen_width);
		
		max_top_y=0;
		max_bottom_y = -(real_tile_width*num_tiles_height-screen_height);
		this.slow_down_speed = slow_down_speed;
	}
	
	public void processFrame(Canvas c, int alpha) {	
		if (alpha != -1) {
			map_paint.setAlpha(alpha);
		}
		
		this.newx += dx;
		this.newy += dy;
		
		if (dx < 0) {
			if (newx < max_right_x) {
				newx = max_right_x;
			}
			dx+=Math.abs(dx)/slow_down_speed;
		}
		else if (dx > 0) {
			if (newx > max_left_x) {
				newx = max_left_x;
			}
			dx-=Math.abs(dx)/slow_down_speed;
		}
		
		if (dy > 0) {
			if (newy > max_top_y) {
				newy = max_top_y;
			}
			dy-=Math.abs(dy)/slow_down_speed;
		}
		else if (dy < 0) {
			if (newy < max_bottom_y) {
				newy = max_bottom_y;
			}
			dy+=Math.abs(dy)/slow_down_speed;
		}	
	
		c.translate(newx, newy);
		
		c.drawBitmap(mapBitmap,0,0,map_paint);
	}
	
	public void setNewXY(float newx, float newy) {
		
		if (newx < max_right_x) {
			newx = max_right_x;
		}
		if (newx > max_left_x) {
			newx = max_left_x;
		}
		if (newy > max_top_y) {
			newy = max_top_y;
		}
		if (newy < max_bottom_y) {
			newy = max_bottom_y;
		}
		
		this.newx = newx;
		this.newy = newy;
	}
	
	public void setDxDy(float dx,float dy,float vx,float vy) {
		this.dx = dx;
		this.dy = dy;
	}
	
	public float getNewX() {
		return newx;
	}
	public float getNewY() {
		return newy;
	}
	
	public TileData getTileAtScreenXY(float x, float y) {
		float x_on_map = newx*-1 + x;
		float y_on_map = newy*-1 + y;
		
		int grid_x = (int)(x_on_map / real_tile_width);	
		int grid_y = (int)(y_on_map / real_tile_height);
		
//Log.e("TTTTT", "TTTTT: grid_x: " + grid_x + "; grid_y: " + grid_y);		
		
		return tiles[grid_y][grid_x];
	}

	public PointF getScreenXYByTile(int grid_x, int grid_y) {
		//adjust for newx/y for map scrolling
		float x = grid_x * real_tile_width + newx;
		float y = grid_y * real_tile_height + newy;

		return new PointF(x,y);
	}
	
	public PointF getAbsScreenXYByTile(int grid_x, int grid_y) {
		//adjust for newx/y for map scrolling
		float x = grid_x * real_tile_width;
		float y = grid_y * real_tile_height;

		return new PointF(x,y);
	}
	
	public int getActualWidth() {
		return actual_width;
	}
	public int getActualHeight() {
		return actual_height;
	}
	
	public int getRealTileWidth() {
		return real_tile_width;
	}
	public int getRealTileHeight() {
		return real_tile_height;
	}
	
	public float getDx() {
		return this.dx;
	}
	
	public float getDy() {
		return this.dy;
	}
	
	public void destroy() {
		map_paint = null;
		mapCanvas = null;
		mapBitmap.recycle();
		mapBitmap = null;
		tiles=null;
		tsf=null;
	}
}

