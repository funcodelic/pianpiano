package com.funcodelic.pianpiano.sheetmusicnotation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

//
//	PageModel is the Model component of the Page MVC classes
//
public class PageModel {
    private BufferedImage sheetMusicImage;
    
    // Maintain the number of the page within the score
    int pageNumber = -1;

    public PageModel(String imagePath, int pageNumber) {
        try {
            sheetMusicImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
            sheetMusicImage = null;
        }
        
        this.pageNumber = pageNumber;
    }

    public BufferedImage getImage() {
        return sheetMusicImage;
    }
    
    public void setPageNumber(int pageNumber) {
    	this.pageNumber = pageNumber;
    }
    
    public int getPageNumber() {
    	return pageNumber;
    }
}
