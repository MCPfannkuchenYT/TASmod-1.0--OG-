package net.tasmod;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.lwjgl.input.Mouse;

import net.minecraft.client.Minecraft;
import net.tasmod.random.SimpleRandomMod;
import net.tasmod.virtual.VirtualKeyboard;
import net.tasmod.virtual.VirtualMouse;

/**
 * Class that contains useful stuff
 * @author Pancake
 */
public final class Utils {

	/** X Position that the mouse moved in-between Ticks */
	public static int dX;
	/** Y Position that the mouse moved in-between Ticks */
	public static int dY;
	/* X Position that the mouse is currently per Tick */
	public static int lastX;
	/* Y Position that the mouse is currently per Tick */
	public static int lastY;

	/** Pitch of the Fake Camera */
	public static float rotationPitch = 0f;
	/** Yaw of the Fake Camera */
	public static float rotationYaw = 0f;
	/** Previous Pitch of the Fake Camera */
	public static float prevRotationPitch = 0f;
	/** Previous Yaw of the Fake Camera */
	public static float prevRotationYaw = 0f;

	/**
	 * Method used to update Yaw and Pitch using Mouse Coordinates.
	 * Used for Fake Camera
	 * @author Mojang Studios
	 */
	public static void setAngles(final float f, final float f1) {
		final float f2 = rotationPitch;
		final float f3 = rotationYaw;
		rotationYaw += f * 0.14999999999999999D;
		rotationPitch -= f1 * 0.14999999999999999D;
		if(rotationPitch < -90F)
			rotationPitch = -90F;
		if(rotationPitch > 90F)
			rotationPitch = 90F;
		prevRotationPitch += rotationPitch - f2;
		prevRotationYaw += rotationYaw - f3;
	}

	/**
	 * Small Utility that deletes a Directory recursively
	 */
	public static boolean deleteDirectory(final File directoryToBeDeleted) {
		final File[] allContents = directoryToBeDeleted.listFiles();
		if (allContents != null)
			for (final File file : allContents)
				deleteDirectory(file);
		return directoryToBeDeleted.delete();
	}

	/**
	 * Used by VirtualMouse, to get the dX for one tick.
	 * The Value dX is being added over frames
	 */
	public static int getDX() {
		final int value = dX;
		dX = 0;
		return value;
	}

	/**
	 * Used by VirtualMouse, to get the dY for one tick.
	 * The Value dY is being added over frames
	 */
	public static int getDY() {
		final int value = dY;
		dY = 0;
		return value;
	}

	public static boolean isObfuscated;


	/**
	 * Transforms the Random Variable for Math.random()
	 */
	public static void transformRandom() throws Exception {
		/* Get Fields for the Random Value used in Math */
		final Field mathRandomField = Class.forName("java.lang.Math$RandomNumberGeneratorHolder").getDeclaredField("randomNumberGenerator");
		mathRandomField.setAccessible(true);
		/* Remove Final */
		final Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(mathRandomField, mathRandomField.getModifiers() & ~Modifier.FINAL);
		/* Replace Random of Math with Modded one */
		mathRandomField.set(null, new SimpleRandomMod());
	}

	/**
	 * Obtains the inaccessible Singleton from the Minecraft Class
	 */
	public static Minecraft obtainMinecraftInstance() throws Exception {
		/* Get Field in Obfuscated or Non-Obfuscated Environment */
		final Class<?> clazz = Class.forName("net.minecraft.client.Minecraft");
		Field theMinecraftField;
		try {
			theMinecraftField = clazz.getDeclaredField("theMinecraft");
		} catch (final Exception e) {
			theMinecraftField = clazz.getDeclaredField("a");
		}
		theMinecraftField.setAccessible(true);
		return (Minecraft) theMinecraftField.get(null);
	}

	/**
	 * Obtains the inaccessible Timer instance from the minecraft class
	 */
	public static Object obtainTimerInstance() throws Exception {
		/* Get Field in Obfuscated or Non-Obfuscated Environment */
		final Class<?> clazz = Class.forName("net.minecraft.client.Minecraft");
		Field theTimerField;
		try {
			theTimerField = clazz.getDeclaredField("timer");
		} catch (final Exception e) {
			theTimerField = clazz.getDeclaredField("X");
		}

	/**
	 * Obtains the inaccessible Timer instance from the minecraft class
	 */
	public static void updateMcApplet(MinecraftApplet mcapplet) throws Exception {
		/* Get Field in Obfuscated or Non-Obfuscated Environment */
		final Class<?> clazz = Class.forName("net.minecraft.client.Minecraft");
		Field mcAppletField = clazz.getDeclaredField("mcApplet");
		mcAppletField.setAccessible(true);
		mcAppletField.set(TASmod.mc, mcapplet);
	}

	public static void lazyMouse() {
		if (VirtualMouse.getEventButton() == 0) VirtualMouse.isButton0Down = VirtualMouse.getEventButtonState();
	}

	public static void lazyKeyboard() {
		if (VirtualKeyboard.getEventKey() == 37) VirtualKeyboard.isKey37Down = VirtualKeyboard.getEventKeyState();
		if (VirtualKeyboard.getEventKey() == 42) VirtualKeyboard.isKey42Down = VirtualKeyboard.getEventKeyState();
		if (VirtualKeyboard.getEventKey() == 54) VirtualKeyboard.isKey54Down = VirtualKeyboard.getEventKeyState();
		if (VirtualKeyboard.getEventKey() == 60) VirtualKeyboard.isKey60Down = VirtualKeyboard.getEventKeyState();
		if (VirtualKeyboard.getEventKey() == 61) VirtualKeyboard.isKey61Down = VirtualKeyboard.getEventKeyState();
		if (VirtualKeyboard.getEventKey() == 51) VirtualKeyboard.isKey51Down = VirtualKeyboard.getEventKeyState();
		if (VirtualKeyboard.getEventKey() == 52) VirtualKeyboard.isKey52Down = VirtualKeyboard.getEventKeyState();
		if (VirtualKeyboard.getEventKey() == 65) VirtualKeyboard.isKey65Down = VirtualKeyboard.getEventKeyState();
	}

	public static int getOriginalX() {
		return Mouse.getDX();
	}

	public static int getOriginalY() {
		return Mouse.getDY();
	}
	
	public static float clampedLerp(float lowerBnd, float upperBnd, float slide) {
		if (slide < 0.0F)
			return lowerBnd;
		else
			return slide > 1.0F ? upperBnd : lowerBnd + (upperBnd - lowerBnd) * slide;
	}

}
