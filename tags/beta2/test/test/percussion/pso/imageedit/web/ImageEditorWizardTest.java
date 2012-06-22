/*
 * COPYRIGHT (c) 1999 - 2008 by Percussion Software, Inc., Woburn, MA USA.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Percussion.
 *
 * test.percussion.pso.imageedit.web ImageEditorWizardTest.java
 *
 */
package test.percussion.pso.imageedit.web;

import static org.junit.Assert.*;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import com.percussion.pso.imageedit.data.ImageSizeDefinition;
import com.percussion.pso.imageedit.data.MasterImageMetaData;
import com.percussion.pso.imageedit.data.SizedImageMetaData;
import com.percussion.pso.imageedit.services.ImageSizeDefinitionManager;
import com.percussion.pso.imageedit.services.cache.ImageCacheManager;
import com.percussion.pso.imageedit.web.ImageEditorWizard;
import com.percussion.pso.imageedit.web.ImagePersistenceManager;
import com.percussion.pso.imageedit.web.ImageResizeManager;

public class ImageEditorWizardTest
{
   Log log = LogFactory.getLog(ImageEditorWizardTest.class);
   Mockery context; 
   TestableImageEditorWizard cut; 
   ImagePersistenceManager imagePersistenceManager; 
   ImageResizeManager imageResizeManager;
   ImageSizeDefinitionManager imageSizeDefinitionManager;
   ImageCacheManager imageCacheManager;
   
   @Before
   public void setUp() throws Exception
   {
      context = new Mockery(); 
      cut = new TestableImageEditorWizard();
      imagePersistenceManager = context.mock(ImagePersistenceManager.class);
      cut.setImagePersistenceManager(imagePersistenceManager);
      imageResizeManager = context.mock(ImageResizeManager.class);
      cut.setImageResizeMgr(imageResizeManager);
      imageSizeDefinitionManager = context.mock(ImageSizeDefinitionManager.class);
      cut.setImageSizeDefMgr(imageSizeDefinitionManager); 
      imageCacheManager = context.mock(ImageCacheManager.class);
      cut.setImageCacheManager(imageCacheManager);
      
   }
   
   //@Test
   public final void testSetupDisplayImage()
   {
      fail("Not yet implemented");
   }
   //@Test
   public final void testCreateScaledImage()
   {
      fail("Not yet implemented");
   }
   
   @Test
   public final void testScaledRectangle()
   {
      Rectangle rect = new Rectangle(100,200,300,400);
      Rectangle result = cut.scaledRectangle(rect, 0.5); 
      assertEquals(50.0,result.getX());
      assertEquals(100.0,result.getY()); 
      assertEquals(200.0,result.getHeight());
      assertEquals(150.0,result.getWidth()); 
   }
   @Test
   public final void testComputeScaleFactor()
   {
      cut.setMaxDisplayHeight(100);
      cut.setMaxDisplayWidth(100); 
      
      double result = cut.computeScaleFactor(50, 50);
      assertEquals(1.0, result); 
      
      result = cut.computeScaleFactor(200, 50);
      assertEquals(2.0, result);
      
      result = cut.computeScaleFactor(50, 200);
      assertEquals(2.0, result); 
      
   }
   
   @Test
   public final void testBuildCropBox()
   {
      SizedImageMetaData simd = new SizedImageMetaData();
      simd.setWidth(100);
      simd.setHeight(200);
      simd.setX(50);
      simd.setY(300);  
         
      Map<String,String> result = cut.buildCropBox(simd);
      assertNotNull(result);
      assertEquals("100", result.get("width")); 
      assertEquals("50", result.get("x"));
      assertEquals("300", result.get("y"));
      assertEquals("200", result.get("height")); 
   }
   
   @Test
   public final void testBuildAllSizesList()
   {
      final ImageSizeDefinition sd1 = new ImageSizeDefinition(){{
         setCode("sd1");
         setLabel("Size1");
      }};
      final ImageSizeDefinition sd2 = new ImageSizeDefinition(){{
         setCode("sd2");
         setLabel("Size2"); 
      }};
      final List<ImageSizeDefinition> sdl = new ArrayList<ImageSizeDefinition>(){{
         add(sd1);
         add(sd2);
      }};
      
      final SizedImageMetaData simd = new SizedImageMetaData() {{
         setSizeDefinition(sd1); 
      }};
      final MasterImageMetaData mimd = new MasterImageMetaData(){{
         addSizedImage(simd); 
      }}; 
      
      context.checking(new Expectations(){{
        one(imageSizeDefinitionManager).getAllImageSizes();
        will(returnValue(sdl));
      }});
      
      List<Map<String,String>> result = cut.buildAllSizesList(mimd);
      assertNotNull(result);
      
      assertEquals("sd1",result.get(0).get("code"));
      assertTrue(result.get(0).containsKey("checked")); 
      assertFalse(result.get(1).containsKey("checked")); 
      context.assertIsSatisfied();
      
   }
   
   @Test
   public final void testCleanEmptySizedImages()
   {
       final SizedImageMetaData s1 = new SizedImageMetaData(){{
          setX(100);
       }};
       final SizedImageMetaData s2 = new SizedImageMetaData();       
       final Map<String, SizedImageMetaData> inmap = new LinkedHashMap<String, SizedImageMetaData>(){{
          put("s1",s1);
          put("s2",s2);
       }};
       
       Map<String,SizedImageMetaData> outmap = cut.cleanEmptySizedImages(inmap);
       assertNotNull(outmap);
       assertEquals(1,outmap.size());
       assertTrue(outmap.containsKey("s1"));
       
   }
   
   private class TestableImageEditorWizard extends ImageEditorWizard
   {

      @Override
      public List<Map<String, String>> buildAllSizedImagesDisplay(
            Map<String, SizedImageMetaData> sizedImages)
      {
         return super.buildAllSizedImagesDisplay(sizedImages);
      }

      @Override
      public Map<String, String> buildCropBox(SizedImageMetaData simd)
      {
         return super.buildCropBox(simd);
      }

      @Override
      public double computeScaleFactor(int height, int width)
      {
         return super.computeScaleFactor(height, width);
      }
      
      
      @Override
      public List<Map<String, String>> buildAllSizesList(
            MasterImageMetaData mimd)
      {
          return super.buildAllSizesList(mimd);
      }

      @Override
      public Map<String, String> buildMasterImageDisplay(
            MasterImageMetaData mimd)
      {
         return super.buildMasterImageDisplay(mimd);
      }

      @Override
      public Rectangle scaledRectangle(Rectangle rect, double scaleFactor)
      {
         return super.scaledRectangle(rect, scaleFactor);
      }

      @Override
      public Map<String, SizedImageMetaData> cleanEmptySizedImages(
            Map<String, SizedImageMetaData> inmap)
      {
         return super.cleanEmptySizedImages(inmap);
      }

      @Override
      public void setImageSizeDefMgr(
            ImageSizeDefinitionManager imageSizeDefMgr)
      {
         super.setImageSizeDefMgr(imageSizeDefMgr);
      }

      @Override
      public void setImageCacheManager(ImageCacheManager imageCacheManager)
      {
         super.setImageCacheManager(imageCacheManager);
      }
      
   }
}
