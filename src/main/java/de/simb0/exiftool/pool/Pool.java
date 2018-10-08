package de.simb0.exiftool.pool;

import de.simb0.exiftool.ExifTool;

public interface Pool<T>  {

   /*
    * @return one of the pooled objects.
    */
   ExifTool get();

   /*
    * @param object T to be return back to pool
    */
   void release(T object);

   /**
    * Shuts down the pool. Should release all resources.
    */
   void shutdown();
}