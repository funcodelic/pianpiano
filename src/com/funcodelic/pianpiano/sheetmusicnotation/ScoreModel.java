package com.funcodelic.pianpiano.sheetmusicnotation;

//
//	This class represents the model component of the Score's MVC classes
//
class ScoreModel {
	
	private String name;
	private String composer;
	
	
	// C'tor
	public ScoreModel() {
		name = "New Score";
	}
	
	public void setName( String name ) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setComposer( String composer ) {
		this.composer = composer;
	}
	
	public String getComposer() {
		return this.composer;
	}

}
