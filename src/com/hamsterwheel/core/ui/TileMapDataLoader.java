package com.hamsterwheel.core.ui;

import java.util.Properties;
import java.util.StringTokenizer;

import android.content.Context;
import android.util.Log;

import com.hamsterwheel.data.DataStore;
import com.hamsterwheel.util.ResourceUtil;

public class TileMapDataLoader {

	private TileData map_tiles[][];
	
	public TileMapDataLoader() {
	}
	
	public void loadStage(Context c, Class raw_class, String map_data_res_id, String row_prefix) {	
		Properties stageProp = DataStore.loadResources(c, ResourceUtil.getRID(raw_class,map_data_res_id));
		
		int width = Integer.parseInt(stageProp.getProperty("width"));
		int height = Integer.parseInt(stageProp.getProperty("height"));
		
		map_tiles = new TileData[height][width];
		
//Log.e("ABAB","width: " + width);		
		for (int i=0; i < height; i++) {
			String map_tiles_str = stageProp.getProperty(row_prefix+i);
			StringTokenizer map_tiles_st = new StringTokenizer(map_tiles_str, ",");	
			int j=0;
			while (map_tiles_st.hasMoreTokens() && j < width) {
				String tile_id_type_info = map_tiles_st.nextToken().trim();
				int sep = tile_id_type_info.indexOf("_");
				String tile_id = tile_id_type_info.substring(0, sep);
				int tile_type =Integer.parseInt(tile_id_type_info.substring(sep+1));
				
//Log.e("ABAB","j: " + j);				
//Log.e("bbbb","bbb height: " + i + "; width: " + j +"; tile id: " + tile_id + "; type: " + tile_type);				
				map_tiles[i][j] = new TileData(tile_id,tile_type,j,i);
				j++;
			}
		}
	}
	
	public TileData[][] getMapTiles() {
		return map_tiles;
	}
}
