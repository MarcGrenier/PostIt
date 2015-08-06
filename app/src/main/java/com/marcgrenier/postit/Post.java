package com.marcgrenier.postit;

import java.util.Date;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;


public class Post{
	
	private float x;
	private float y;
	private float x1;
	private float y1;
	
	private String content;
	private String creator;
	private Date dateToKill;
	
	private int fontSize = 33;
	private int dimension = 290;
	
	public Post(float x, float y, String creator ,String content, Date dateToKill) {
		this.x = x;
		this.y = y;
		this.x1 = x + this.dimension;
		this.y1 = y + this.dimension;
		
		this.content = content;
		this.creator = creator;
		
		this.dateToKill = dateToKill;
		
	}

	public void draw(Canvas canvas){
		Paint paint = new Paint();
		
		paint.setColor(Color.BLACK);
		paint.setStyle(Paint.Style.STROKE);
		
		Path path2 = new Path();
		path2.moveTo(x, y);
		path2.lineTo(x1,y);
		path2.lineTo(x1,y1);
		path2.lineTo(x+((x1-x)*0.1f),y1);
		path2.lineTo(x,(y1-(y1-y)*0.1f));
		
		path2.close();
		
		canvas.drawPath(path2, paint);
		
		paint.setColor(Color.YELLOW);
        paint.setStyle(Paint.Style.FILL);
        
		Path path = new Path();
		path.moveTo(x, y);
		path.lineTo(x1,y);
		path.lineTo(x1,y1);
		path.lineTo(x+((x1-x)*0.1f),y1);
		path.lineTo(x,(y1-(y1-y)*0.1f));
		
		path.close();
		
		canvas.drawPath(path, paint);
		
		
		Path path1 = new Path();
		paint.setColor(Color.rgb(150, 150, 0));
		path1.moveTo(x,(y1-(y1-y)*0.1f));
		path1.lineTo(x+((x1-x)*0.1f),y1);
		path1.lineTo(x +((x1-x)*0.1f),(y1-(y1-y)*0.1f));
		path1.lineTo(x,(y1-(y1-y)*0.1f));
		
		path1.close();
		
		canvas.drawPath(path1, paint);
		
		paint.setColor(Color.BLACK);
		paint.setTextSize(fontSize);
		canvas.drawText(this.content, x+fontSize, y+fontSize+25, paint);

	}
	
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}

	public String getCreator() {
		return creator;
	}

	public String getContent() {
		return content;
	}
	
	public Date getDateToKill() {
		return dateToKill;
	}
	
}
