/*
 * Copyright 2017, Yahoo! Inc. Licensed under the terms of the
 * Apache License 2.0. See LICENSE file at the project root for terms.
 */

package com.yahoo.memory;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

public class CommonBufferTest {

  @Test
  public void checkSetGet() {
    int memCapacity = 60; //must be at least 60
    try (WritableDirectHandle wrh = WritableMemory.allocateDirect(memCapacity)) {
      WritableMemory mem = wrh.get();
      WritableBuffer buf = mem.asWritableBuffer();
      assertEquals(buf.getCapacity(), memCapacity);
      setGetTests(buf);
    }
  }

  public static void setGetTests(WritableBuffer buf) {
    buf.putBoolean(true);
    buf.putBoolean(false);
    buf.putByte((byte) -1);
    buf.putByte((byte)0);
    buf.putChar('A');
    buf.putChar('Z');
    buf.putShort(Short.MAX_VALUE);
    buf.putShort(Short.MIN_VALUE);
    buf.putInt(Integer.MAX_VALUE);
    buf.putInt(Integer.MIN_VALUE);
    buf.putFloat(Float.MAX_VALUE);
    buf.putFloat(Float.MIN_VALUE);
    buf.putLong(Long.MAX_VALUE);
    buf.putLong(Long.MIN_VALUE);
    buf.putDouble(Double.MAX_VALUE);
    buf.putDouble(Double.MIN_VALUE);

    buf.resetPosition();

    assertEquals(buf.getBoolean(), true);
    assertEquals(buf.getBoolean(), false);
    assertEquals(buf.getByte(), (byte) -1);
    assertEquals(buf.getByte(), (byte)0);
    assertEquals(buf.getChar(), 'A');
    assertEquals(buf.getChar(), 'Z');
    assertEquals(buf.getShort(), Short.MAX_VALUE);
    assertEquals(buf.getShort(), Short.MIN_VALUE);
    assertEquals(buf.getInt(), Integer.MAX_VALUE);
    assertEquals(buf.getInt(), Integer.MIN_VALUE);
    assertEquals(buf.getFloat(), Float.MAX_VALUE);
    assertEquals(buf.getFloat(), Float.MIN_VALUE);
    assertEquals(buf.getLong(), Long.MAX_VALUE);
    assertEquals(buf.getLong(), Long.MIN_VALUE);
    assertEquals(buf.getDouble(), Double.MAX_VALUE);
    assertEquals(buf.getDouble(), Double.MIN_VALUE);
  }

  @Test
  public void checkSetGetArrays() {
    int memCapacity = 32;
    try (WritableDirectHandle wrh = WritableMemory.allocateDirect(memCapacity)) {
      WritableMemory mem = wrh.get();
      WritableBuffer buf = mem.asWritableBuffer();
      assertEquals(buf.getCapacity(), memCapacity);
      setGetArraysTests(buf);
    }
  }

  public static void setGetArraysTests(WritableBuffer buf) {
    int words = 4;

    boolean[] srcArray1 = {true, false, true, false};
    boolean[] dstArray1 = new boolean[words];
    buf.resetPosition();
    buf.fill((byte)127);
    buf.resetPosition();
    buf.putBooleanArray(srcArray1, 0, words);
    buf.resetPosition();
    buf.getBooleanArray(dstArray1, 0, words);
    for (int i=0; i<words; i++) {
      assertEquals(dstArray1[i], srcArray1[i]);
    }

    byte[] srcArray2 = { 1, -2, 3, -4 };
    byte[] dstArray2 = new byte[4];
    buf.resetPosition();
    buf.putByteArray(srcArray2, 0, words);
    buf.resetPosition();
    buf.getByteArray(dstArray2, 0, words);
    for (int i=0; i<words; i++) {
      assertEquals(dstArray2[i], srcArray2[i]);
    }

    char[] srcArray3 = { 'A', 'B', 'C', 'D' };
    char[] dstArray3 = new char[words];
    buf.resetPosition();
    buf.putCharArray(srcArray3, 0, words);
    buf.resetPosition();
    buf.getCharArray(dstArray3, 0, words);
    for (int i=0; i<words; i++) {
      assertEquals(dstArray3[i], srcArray3[i]);
    }

    double[] srcArray4 = { 1.0, -2.0, 3.0, -4.0 };
    double[] dstArray4 = new double[words];
    buf.resetPosition();
    buf.putDoubleArray(srcArray4, 0, words);
    buf.resetPosition();
    buf.getDoubleArray(dstArray4, 0, words);
    for (int i=0; i<words; i++) {
      assertEquals(dstArray4[i], srcArray4[i], 0.0);
    }

    float[] srcArray5 = { (float)1.0, (float)-2.0, (float)3.0, (float)-4.0 };
    float[] dstArray5 = new float[words];
    buf.resetPosition();
    buf.putFloatArray(srcArray5, 0, words);
    buf.resetPosition();
    buf.getFloatArray(dstArray5, 0, words);
    for (int i=0; i<words; i++) {
      assertEquals(dstArray5[i], srcArray5[i], 0.0);
    }

    int[] srcArray6 = { 1, -2, 3, -4 };
    int[] dstArray6 = new int[words];
    buf.resetPosition();
    buf.putIntArray(srcArray6, 0, words);
    buf.resetPosition();
    buf.getIntArray(dstArray6, 0, words);
    for (int i=0; i<words; i++) {
      assertEquals(dstArray6[i], srcArray6[i]);
    }

    long[] srcArray7 = { 1, -2, 3, -4 };
    long[] dstArray7 = new long[words];
    buf.resetPosition();
    buf.putLongArray(srcArray7, 0, words);
    buf.resetPosition();
    buf.getLongArray(dstArray7, 0, words);
    for (int i=0; i<words; i++) {
      assertEquals(dstArray7[i], srcArray7[i]);
    }

    short[] srcArray8 = { 1, -2, 3, -4 };
    short[] dstArray8 = new short[words];
    buf.resetPosition();
    buf.putShortArray(srcArray8, 0, words);
    buf.resetPosition();
    buf.getShortArray(dstArray8, 0, words);
    for (int i=0; i<words; i++) {
      assertEquals(dstArray8[i], srcArray8[i]);
    }
  }

  @Test
  public void checkSetGetPartialArraysWithOffset() {
    int memCapacity = 32;
    try (WritableDirectHandle wrh = WritableMemory.allocateDirect(memCapacity)) {
      WritableMemory mem = wrh.get();
      WritableBuffer buf = mem.asWritableBuffer();
      assertEquals(buf.getCapacity(), memCapacity);
      setGetPartialArraysWithOffsetTests(buf);
    }
  }

  public static void setGetPartialArraysWithOffsetTests(WritableBuffer buf) {
    int items= 4;
    boolean[] srcArray1 = {true, false, true, false};
    boolean[] dstArray1 = new boolean[items];
    buf.resetPosition();
    buf.putBooleanArray(srcArray1, 2, items/2);
    buf.resetPosition();
    buf.getBooleanArray(dstArray1, 2, items/2);
    for (int i=2; i<items; i++) {
      assertEquals(dstArray1[i], srcArray1[i]);
    }

    byte[] srcArray2 = { 1, -2, 3, -4 };
    byte[] dstArray2 = new byte[items];
    buf.resetPosition();
    buf.putByteArray(srcArray2, 2, items/2);
    buf.resetPosition();
    buf.getByteArray(dstArray2, 2, items/2);
    for (int i=2; i<items; i++) {
      assertEquals(dstArray2[i], srcArray2[i]);
    }

    char[] srcArray3 = { 'A', 'B', 'C', 'D' };
    char[] dstArray3 = new char[items];
    buf.resetPosition();
    buf.putCharArray(srcArray3, 2, items/2);
    buf.resetPosition();
    buf.getCharArray(dstArray3, 2, items/2);
    for (int i=2; i<items; i++) {
      assertEquals(dstArray3[i], srcArray3[i]);
    }

    double[] srcArray4 = { 1.0, -2.0, 3.0, -4.0 };
    double[] dstArray4 = new double[items];
    buf.resetPosition();
    buf.putDoubleArray(srcArray4, 2, items/2);
    buf.resetPosition();
    buf.getDoubleArray(dstArray4, 2, items/2);
    for (int i=2; i<items; i++) {
      assertEquals(dstArray4[i], srcArray4[i], 0.0);
    }

    float[] srcArray5 = { (float)1.0, (float)-2.0, (float)3.0, (float)-4.0 };
    float[] dstArray5 = new float[items];
    buf.resetPosition();
    buf.putFloatArray(srcArray5, 2, items/2);
    buf.resetPosition();
    buf.getFloatArray(dstArray5, 2, items/2);
    for (int i=2; i<items; i++) {
      assertEquals(dstArray5[i], srcArray5[i], 0.0);
    }

    int[] srcArray6 = { 1, -2, 3, -4 };
    int[] dstArray6 = new int[items];
    buf.resetPosition();
    buf.putIntArray(srcArray6, 2, items/2);
    buf.resetPosition();
    buf.getIntArray(dstArray6, 2, items/2);
    for (int i=2; i<items; i++) {
      assertEquals(dstArray6[i], srcArray6[i]);
    }

    long[] srcArray7 = { 1, -2, 3, -4 };
    long[] dstArray7 = new long[items];
    buf.resetPosition();
    buf.putLongArray(srcArray7, 2, items/2);
    buf.resetPosition();
    buf.getLongArray(dstArray7, 2, items/2);
    for (int i=2; i<items; i++) {
      assertEquals(dstArray7[i], srcArray7[i]);
    }

    short[] srcArray8 = { 1, -2, 3, -4 };
    short[] dstArray8 = new short[items];
    buf.resetPosition();
    buf.putShortArray(srcArray8, 2, items/2);
    buf.resetPosition();
    buf.getShortArray(dstArray8, 2, items/2);
    for (int i=2; i<items; i++) {
      assertEquals(dstArray8[i], srcArray8[i]);
    }
  }

  @Test
  public void checkSetClearMemoryRegions() {
    int memCapacity = 64; //must be 64
    try (WritableDirectHandle wrh1 = WritableMemory.allocateDirect(memCapacity)) {
      WritableMemory mem = wrh1.get();
      WritableBuffer buf = mem.asWritableBuffer();
      assertEquals(buf.getCapacity(), memCapacity);

      setClearMemoryRegionsTests(buf); //requires println enabled to visually check
      buf.resetPosition();
      for (int i = 0; i < memCapacity; i++) {
        assertEquals(mem.getByte(i), 0);
      }
    }
  }

  //enable println statements to visually check
  public static void setClearMemoryRegionsTests(WritableBuffer buf) {
    int accessCapacity = (int)buf.getCapacity();

  //define regions
    int reg1Start = 0;
    int reg1Len = 28;
    int reg2Start = 28;
    int reg2Len = 32;

    //set region 1
    byte b1 = 5;
    buf.setStartPositionEnd(reg1Start, reg1Start, reg1Len);
    buf.fill(b1);
    buf.resetPosition();
    for (int i=reg1Start; i<reg1Len+reg1Start; i++) {
      assertEquals(buf.getByte(), b1);
    }
    //println(buf.toHexString("Region1 to 5", reg1Start, reg1Len));

    //set region 2
    byte b2 = 7;
    buf.setStartPositionEnd(reg2Start, reg2Start, reg2Start + reg2Len);
    buf.fill(b2);
    //println(mem.toHexString("Fill", 0, (int)mem.getCapacity()));
    buf.resetPosition();
    for (int i=reg2Start; i<reg2Start+reg2Len; i++) {
      assertEquals(buf.getByte(), b2);
    }
    //println(buf.toHexString("Region2 to 7", reg2Start, reg2Len));

    //clear region 1
    byte zeroByte = 0;
    buf.setStartPositionEnd(reg1Start, reg1Start, reg2Len);
    buf.resetPosition();
    buf.clear();
    buf.resetPosition();
    for (int i=reg1Start; i<reg1Start+reg1Len; i++) {
      assertEquals(buf.getByte(), zeroByte);
    }
    //println(buf.toHexString("Region1 cleared", reg1Start, reg1Len));

    //clear region 2
    buf.setStartPositionEnd(reg2Start, reg2Start, reg2Start + reg2Len);
    buf.resetPosition();
    buf.clear();
    buf.resetPosition();
    for (int i=reg2Start; i<reg2Len+reg2Start; i++) {
      assertEquals(buf.getByte(), zeroByte);
    }
    //println(buf.toHexString("Region2 cleared", reg2Start, reg2Len));

    //set all to ones
    buf.setStartPositionEnd(reg1Start, reg1Start, accessCapacity);
    byte b4 = 127;
    buf.resetPosition();
    buf.fill(b4);
    buf.resetPosition();
    for (int i=0; i<accessCapacity; i++) {
      assertEquals(buf.getByte(), b4);
    }
    //println(buf.toHexString("Region1 + Region2 all ones", 0, accessCapacity));

    //clear all
    buf.resetPosition();
    buf.clear();
    buf.resetPosition();
    for (int i=0; i<accessCapacity; i++) {
      assertEquals(buf.getByte(), zeroByte);
    }
    //println(buf.toHexString("Region1 + Region2 cleared", 0, accessCapacity));
  }

  @Test
  public void checkToHexStringAllMem() {
    int memCapacity = 48; //must be 48
    try (WritableDirectHandle wrh1 = WritableMemory.allocateDirect(memCapacity)) {
      WritableMemory mem = wrh1.get();
      WritableBuffer buf = mem.asWritableBuffer();
      assertEquals(buf.getCapacity(), memCapacity);
      toHexStringAllMemTests(buf); //requires println enabled to visually check
    }
  }

  //enable println to visually check
  public static void toHexStringAllMemTests(WritableBuffer buf) {
    int memCapacity = (int)buf.getCapacity();

    for (int i=0; i<memCapacity; i++) {
      buf.putByte((byte)i);
    }

    //println(buf.toHexString("Check toHexString(0, 48) to integers", 0, memCapacity));
    //println(buf.toHexString("Check toHexString(8, 40)", 8, 40));
  }

  @Test
  public void printlnTest() {
    println("PRINTING: "+this.getClass().getName());
  }

  /**
   * @param s value to print
   */
  static void println(String s) {
    //System.out.println(s); //disable here
  }

}
