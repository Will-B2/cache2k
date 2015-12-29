package org.cache2k;

/*
 * #%L
 * cache2k API only package
 * %%
 * Copyright (C) 2000 - 2015 headissue GmbH, Munich
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

/**
 * @author Jens Wilke; created: 2013-12-21
 */
public interface MutableCacheEntry<K, T> extends CacheEntry<K, T> {

  /**
   * True if a mapping exists in the cache, never invokes the loader / cache source.
   */
  boolean exists();
  void setValue(T v);
  void setException(Throwable ex);
  void remove();

}
