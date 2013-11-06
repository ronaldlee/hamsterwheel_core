package com.hamsterwheel.audio;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

public class MediaPlayerPool {
    private static final String TAG = "MediaPlayerPool";

    private Context context;
    private int streamType;

    private HashMap<Integer, ArrayList<PoolMediaPlayer>> pools;

    private boolean is_pools_released=false;
    class PoolMediaPlayer extends MediaPlayer {
    	boolean isOutOfPool;
    	MediaPlayer mplayer;
    	
    	PoolMediaPlayer(boolean isOutOfPool, MediaPlayer mplayer) {
    		this.isOutOfPool = isOutOfPool;
    		this.mplayer = mplayer;
    	}
    	void setIsOutOfPool(boolean isOut) {
    		isOutOfPool = isOut;
    	}
    	
    	@Override
    	public void start() {
    		mplayer.start();
    	}
    	@Override
    	public void stop() {
    		mplayer.stop();
    	}
    	@Override
    	public void release() {
    		mplayer.release();
    	}
    	@Override
    	public void seekTo(int msec) {
    		mplayer.seekTo(msec);
    	}
    }
    
    public MediaPlayerPool(Context context, int streamType) {
        this.context = context;
        this.streamType = streamType;
        pools = new HashMap<Integer, ArrayList<PoolMediaPlayer>>();
    }
    
    public void resetIsPoolReleasedFlag() {
    	is_pools_released = false;
    }

    public boolean isPoolReleased() {
    	return is_pools_released;
    }
    
    public boolean hasMedia(int resourceId) {
//Log.e("mediaplayerpool","pool size: " + pools.size()); 	
        return pools.keySet().contains(resourceId);
    }

    public void addMedia(int resourceId, int poolSize) {
        ArrayList<PoolMediaPlayer> pool = pools.get(resourceId);
        if (pool == null) {
            pool = new ArrayList<PoolMediaPlayer>();
        }
        for (int i = 0; i < poolSize; i++) {
            pool.add(createMediaPlayer(resourceId));
        }
        pools.put(resourceId, pool);
    }

    public void playMedia(int resourceId) {
        ArrayList<PoolMediaPlayer> pool = pools.get(resourceId);
        int poolSize = pool.size();
        boolean isStarted = false;
        for (PoolMediaPlayer player: pool) {
        	if (!player.isOutOfPool) {
        		player.setIsOutOfPool(true);
        		player.start();
        		isStarted = true;
        		break;
        	}
        }
        
        if (!isStarted) {
        	pool.get(0).start();
        }
        
        /*
        if (pool.size() > 0) {
            player = pool.remove(0);
        }
        if (player == null) {
        	Log.e(TAG, "Pool is empty for resource " + resourceId);
        } else {
            player.start();
        }
        */
    }

    public void release() {
Log.e("mediaplayerpool","release");    	
        for (Integer key : pools.keySet()) {
            ArrayList<PoolMediaPlayer> pool = pools.get(key);
            for (PoolMediaPlayer player : pool) {
                if (player.isPlaying()) {
                	player.stop();
                }
                player.release();
            }
            pool.clear();
        }
        pools.clear();
        is_pools_released=true;
    }

    private PoolMediaPlayer createMediaPlayer(final int resourceId) {
        MediaPlayer mp = MediaPlayer.create(context, resourceId);
        mp.setAudioStreamType(streamType);

        PoolMediaPlayer poolPlayer = new PoolMediaPlayer(false, mp);
        poolPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer player) {
                player.seekTo(0);
                returnToPool(resourceId, player);
            }
        });
        
        return poolPlayer;
    }

    private void returnToPool(int resourceId, MediaPlayer player) {
    	((PoolMediaPlayer)player).setIsOutOfPool(false);
    }

} 