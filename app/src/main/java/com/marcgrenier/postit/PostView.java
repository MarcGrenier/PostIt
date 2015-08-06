package com.marcgrenier.postit;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import android.view.Display;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.WindowManager;


public class PostView extends View{

	private ScaleGestureDetector scaleDetector;
	
	private float mScaleFactor = 1.f, lastScaleFactor = 1.0f;
	private float translateX = 0.0f ,translateY = 0.0f;
	private float lastX = 0.0f, lastY=0.0f;
	private float realX = -10000.0f, realY = -10000.0f;
	
	private ArrayList<Post> post = new ArrayList<Post>();
	
	private WindowManager wm;
	protected Point size = new Point();
	
	private boolean isScaling = false;
	
	public PostView(Context context) {
		super(context);
		this.setBackgroundColor(Color.BLACK);
		
		wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getSize(size);
			
		
		scaleDetector =  new ScaleGestureDetector(context, new ScaleListener());		
	}
	
	
	@Override
	protected void onDraw(Canvas canvas) {		
		canvas.save();
		
		canvas.scale(mScaleFactor, mScaleFactor);
		canvas.translate(realX, realY);
		
		Iterator<Post> i = post.iterator();
		while(i.hasNext()){
			Post postit = i.next();

			if(isVisible(postit)){
					postit.draw(canvas);
			}
		};
		
		canvas.restore();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getActionMasked();
	
		scaleDetector.onTouchEvent(event);
		
		switch(action){
			case MotionEvent.ACTION_MOVE:
				if(!scaleDetector.isInProgress() && !isScaling){
					translateX = lastX - event.getX();
					translateY = lastY - event.getY();
					
					lastX = event.getX();
					lastY = event.getY();
					
					realX -= translateX / mScaleFactor;
					realY -= translateY / mScaleFactor;
					
					if(realX > 0){
						realX = 0;
					}
					if(realY > 0){
						realY = 0;
					}
					
					invalidate();
				}
				else{
					isScaling = true;
					//trouve le centre de l'écran et ajoute la moitié de la lageur de l'écran selon le zoom
					float midX = realX - (size.x/2/lastScaleFactor);
					realX = midX + (size.x/2/mScaleFactor);
					float midY = realY - (size.y/2/lastScaleFactor);
					realY = midY + (size.y/2/mScaleFactor);
										
					invalidate();
				}
				break;
				
			case MotionEvent.ACTION_UP:
				isScaling = false;
				break;
				
			case MotionEvent.ACTION_DOWN:	
				lastX = event.getX();
				lastY = event.getY();
				break;
		};
		
		return true;
	}
	
	private boolean isVisible(Post postIt){
		
		boolean visible = false;
		
		if(postIt.getX()>Math.abs(realX)-270 && postIt.getX()< Math.abs(realX) + (size.x/mScaleFactor)-20){
			if(postIt.getY()> Math.abs(realY)-270 && postIt.getY() < Math.abs(realY) + (size.y/mScaleFactor)-20){
				
				visible = true;
			
			}
		}
		
		return visible;
	}
	
	public void addPost(String noteText){
		Random r = new Random();
		post.add(new Post(r.nextInt()%10000, r.nextInt()%10000, "Me", noteText, new Date()));
	}
	
	
	/**
	 * Classe interne écouteur pour le scale du postView
	 * @author Marc
	 *
	 */
	private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener{
				
		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			lastScaleFactor = mScaleFactor;
			mScaleFactor = mScaleFactor * detector.getScaleFactor();

	        // Don't let the object get too small or too large.
	        mScaleFactor = Math.max(0.5f, Math.min(mScaleFactor, 1.0f));
	        
			invalidate();
			
			return true;
		}	
		
	}
	
	
}
