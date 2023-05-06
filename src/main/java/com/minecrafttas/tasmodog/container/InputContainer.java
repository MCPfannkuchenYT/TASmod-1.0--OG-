package com.minecrafttas.tasmodog.container;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.minecrafttas.tasmodog.TASmod;

/**
 * Input container containing inputs and other tasmod data for all ticks
 * @author Pancake
 */
public class InputContainer {

	private List<Tick> ticks;
	private Tick currentTick;
	private int nextTick;
	private boolean isActive, isRecording;
	
	/**
	 * Initialize Input container
	 */
	public InputContainer() {
		this.ticks = new ArrayList<>();
		this.currentTick = new Tick();
		this.nextTick = 0;
		this.isActive = true;
		this.isRecording = false;
	}
	
	/**
	 * Post Initialize Input container with TASmod instance
	 * @param tasmod TASmod instance
	 */
	public void init(TASmod tasmod) {
		
	}
	
	/**
	 * Tick Input container
	 */
	public void tick() {
		if (!this.isActive) {
			this.currentTick = new Tick();
			return;
		}
		
		this.currentTick = (this.ticks.size() >= this.nextTick || this.isRecording) ? new Tick() : this.ticks.get(this.nextTick);
		this.nextTick++;
	}
	
	/**
	 * Save tick data to file
	 * @param file File
	 * @throws Exception Filesystem exception
	 */
	public void save(File file) throws Exception {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
		oos.writeObject(this.ticks);
		oos.close();
	}
	
	/**
	 * Load tick data from file
	 * @param file File
	 * @throws Exception Filesystem exception
	 */
	@SuppressWarnings("unchecked")
	public void load(File file) throws Exception {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
		this.ticks = (List<Tick>) ois.readObject();
		ois.close();
	}
	
	/**
	 * Set input container into recording mode
	 * @param isRecording Recording mode
	 */
	public void setRecording(boolean isRecording) {
		this.isRecording = isRecording;
	}
	
	/**
	 * Disables the input container
	 */
	public void disable() {
		this.isActive = false;
	}

	/**
	 * Return current tick data
	 * @return Current tick data
	 */
	public Tick getCurrentTickData() {
		return this.currentTick;
	}

	/**
	 * Return recording state
	 * @return Recording state
	 */
	public boolean isRecording() {
		return this.isRecording;
	}

	/**
	 * Return active state
	 * @return Active state
	 */
	public boolean isActive() {
		return this.isActive;
	}

}
