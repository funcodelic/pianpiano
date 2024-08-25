package com.funcodelic.pianpiano.sheetmusicnotation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

//
//	PageModel is the Model component of the Page MVC classes
//
class PageModel {
    private BufferedImage sheetMusicImage;
    private String imagePath;
    
    // The number of the page within the score
    private int pageNumber = -1;

    PageModel( String imagePath, int pageNumber ) {
    	this.imagePath = imagePath;
    	this.pageNumber = pageNumber;
    	
    	// Read the image into the buffered image
        try {
            sheetMusicImage = ImageIO.read( new File( this.imagePath ) );
        } catch ( IOException e ) {
            e.printStackTrace();
            sheetMusicImage = null;
        }
    }

    BufferedImage getImage() {
        return sheetMusicImage;
    }
    
    void setPageNumber( int pageNumber ) {
    	this.pageNumber = pageNumber;
    }
    
    int getPageNumber() {
    	return pageNumber;
    }
}
