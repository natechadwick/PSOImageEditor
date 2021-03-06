/*
 * COPYRIGHT (c) 1999 - 2008 by Percussion Software, Inc., Woburn, MA USA.
 * All rights reserved. This material contains unpublished, copyrighted
 * work including confidential and proprietary information of Percussion.
 *
 * com.percussion.pso.imageedit.services.impl ImageSizeDefinitionManagerImpl.java
 *
 */
package com.percussion.pso.imageedit.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.percussion.pso.imageedit.data.ImageSizeDefinition;
import com.percussion.pso.imageedit.services.ImageSizeDefinitionManager;


/**
 * Service for getting defined image sizes. 
 *
 * @author DavidBenua
 *
 */
public class ImageSizeDefinitionManagerImpl implements ImageSizeDefinitionManager
{
   private static Log log = LogFactory.getLog(ImageSizeDefinitionManagerImpl.class);
   
   private List<ImageSizeDefinition> sizes; 
   
   private String sizedImageNodeName; 
   
   private String sizedImagePropertyName;
   
   /**
    * The path name of the image to be displayed
    * when there is no image. 
    */
   private String failureImagePath; 
   
   /**
    * 
    */
   public ImageSizeDefinitionManagerImpl()
   {
      sizes = new ArrayList<ImageSizeDefinition>(); 
   }
   
   /**
    * @see com.percussion.pso.imageedit.services.ImageSizeDefinitionManager#getAllImageSizes()
    */
   public List<ImageSizeDefinition> getAllImageSizes()
   {
      return sizes;
   }
   /**
    * @see com.percussion.pso.imageedit.services.ImageSizeDefinitionManager#getImageSize(java.lang.String)
    */
   public ImageSizeDefinition getImageSize(String code)
   {
      if(StringUtils.isEmpty(code))
      {
         throw new IllegalArgumentException("image size code must not be null"); 
      }
      for(ImageSizeDefinition sz : sizes)
      {
         if(sz.getCode().equals(code))
         {
            return sz; 
         }
      }
      log.debug("request for image size " + code + " not found "); 
      return null;
   }

   /**
    * @return the sizes
    */
   public List<ImageSizeDefinition> getSizes()
   {
      return sizes;
   }

   /**
    * @param sizes the sizes to set
    */
   public void setSizes(List<ImageSizeDefinition> sizes)
   {
      this.sizes = sizes;
   }

   /**
    * @return the sizedImageNodeName
    */
   public String getSizedImageNodeName()
   {
      return sizedImageNodeName;
   }

   /**
    * @param sizedImageNodeName the sizedImageNodeName to set
    */
   public void setSizedImageNodeName(String sizedImageNodeName)
   {
      this.sizedImageNodeName = sizedImageNodeName;
   }

   /**
    * @return the sizedImagePropertyName
    */
   public String getSizedImagePropertyName()
   {
      return sizedImagePropertyName;
   }

   /**
    * @param sizedImagePropertyName the sizedImagePropertyName to set
    */
   public void setSizedImagePropertyName(String sizedImagePropertyName)
   {
      this.sizedImagePropertyName = sizedImagePropertyName;
   }

   /**
    * @return the failureImagePath
    */
   public String getFailureImagePath()
   {
      return failureImagePath;
   }

   /**
    * @param failureImagePath the failureImagePath to set
    */
   public void setFailureImagePath(String failureImagePath)
   {
      this.failureImagePath = failureImagePath;
   }
}
