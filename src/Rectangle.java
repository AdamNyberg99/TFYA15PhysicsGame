import java.awt.Graphics;

class Point {
	double x;
	double y;

	Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	// Kollar om tv� punkter �r lika
	boolean equals(Point p) {
		if ((int)this.x == (int)p.x && (int) this.y == (int) p.y) {
			return true;
		}
		return false;
	}
}

public class Rectangle {
	Point[] points;
	double currentOrientation;
	private int recWidth;
	private int recHeight;
	private int recX;
	private int recY;

	Rectangle(int playerX, int playerY, int playerWidth, int playerHeight) {
		this.recWidth = playerWidth;
		this.recHeight = playerHeight;
		this.recX = playerX;
		this.recY = playerY;

		points = new Point[playerWidth * 2 + playerHeight * 2];
		// Skapar en ram f�r rektangeln utifr�n dess fyra h�rn punkter
		int arrayPos = 0;

		for (int i = playerX; i < playerX + playerWidth; i++) {
			points[arrayPos] = new Point(i, playerY);
			points[arrayPos+1] = new Point(i, playerY + playerHeight);
			arrayPos += 2;
		}
		for (int i = playerY; i < playerY + playerHeight; i++) {
			points[arrayPos] = new Point(playerX, i);
			points[arrayPos+1] = new Point(playerX + playerWidth, i);
			arrayPos +=2;
		}
		// En punkt (�vre v�nstra h�rnet skrivs tv� g�nger utav for-looparna medans nedre h�gra h�rnet aldrig skrivs ut d�rf�r
		// skriver vi �ver en av de tv� platser i array:en som inneh�ller �vre v�nstra h�rnet med punkten f�r undre h�gra.
		points[0] = new Point(playerX + playerWidth, playerY + playerHeight);
        
    }
   
    // Roterar rektangeln genom att rotera alla punkter i rektangelns ram runt origo
    // vinkeln (direction) �r i radianer!
    void rotate(double direction) {
        double toRotate = currentOrientation - direction;
		currentOrientation = direction;

		for (Point p : points) {
			// flyttar rektangelns centrum till origo
			double tempX = p.x - recX - recWidth / 2;
			double tempY = p.y - recY - recHeight / 2;

			// Roterar h�rnen
			double rotatedX = (tempX * Math.cos(toRotate)) - (tempY * Math.sin(toRotate));
			double rotatedY = (tempX * Math.sin(toRotate)) + (tempY * Math.cos(toRotate));

			// Flyttar tillbaks rektangeln till dess ursprungliga position
			p.x = rotatedX + recX + recWidth / 2;
			p.y = rotatedY + recY + recHeight / 2;

		}

	}
    
    // Returnerar l�ngden f�r rektangelns diagonal
    double diagonal() {
    	double diag = Math.sqrt(recWidth*recWidth+recHeight*recHeight);
    	
		return diag;	
    }
    
    //Returnerar minsta avst�ndet fr�n centerpunkten till en punkt p� ramen (omkretsen) f�r rektangeln
    double minDistaceToBorder() {
    	
    	if(recWidth<recHeight) {
    		return recWidth/2;
    	}
        	
    	return recHeight/2;
	
    }
    
    // returnerar rektangelns mittpunkt
    Point center() {
    	double x = recX+ recWidth/2;
    	double y = recY + recHeight/2;
    	return new Point(x,y);
    }
    
    // Kollar om rektangeln �verlappar en annan rektangel (R), returnerar true om de �verlappar och annars false
    boolean intersect(Rectangle R) {
    	double xDiffrence = this.center().x-R.center().x;
    	double yDifference = this.center().y-R.center().y;
    	double distCenterToCenter= Math.sqrt(xDiffrence*xDiffrence+yDifference*yDifference);
    	
    	// Om avst�ndet mellan mittpunkterna �r st�rre �n sammanlagda l�ngden f�r diagonalerna delat p� tv� kan rektanglarna
    	// int �verlappa.
    	if(distCenterToCenter > this.diagonal()+R.diagonal()) {
    		return false;
    	}
    	
    	// Om avst�ndet mellan mittpunkterna �r mindre �n kortaste str�ckan fr�n en av rektanglarnas centerpunkt till en punkt p� ramen
    	// D� �verlappar alltid rektanglarna
    	if(R.minDistaceToBorder() < this.minDistaceToBorder()*2) {
    		if(distCenterToCenter < R.minDistaceToBorder()*2) {
    			return true;
    		}
    	} else {
    		if(distCenterToCenter < this.minDistaceToBorder()*2) {
    			return true;
    		} else if(distCenterToCenter < R.minDistaceToBorder()) {
    			return true;
    		}
    	}
    	
    	// I de fall som inte t�cks av if-satserna f�r avst�ndet mellan centerpunkterna och diagonalerna.  D� j�mf�r vi alla punkter 
    	//f�r de tv� rektanglarna och om tv� punkter i dess ramar �verlappar �verlappar, �verlappar �ven rektanglarna.
    	for(Point p1: points) {
    		for(Point p2: R.points) {
    			if(p1.equals(p2)) {
    				return true;
    			}
    		}
    	}
    	
		return false;
   	
    }
    
    // M�lar rektangeln genom att m�la alla punkter i dess ram.
    void draw(Graphics g) {
    	for(Point p: points) {
    		g.drawLine((int) p.x,(int) p.y,(int) p.x,(int) p.y);
    	}
    }
}
