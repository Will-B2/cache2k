package org.cache2k.xmlConfig;

/*
 * #%L
 * cache2k XML configuration
 * %%
 * Copyright (C) 2000 - 2016 headissue GmbH, Munich
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import org.cache2k.CacheManager;
import org.cache2k.CacheMisconfigurationException;
import org.cache2k.configuration.Cache2kConfiguration;
import org.cache2k.core.spi.CacheConfigurationProvider;
import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;

/**
 * Hooks into cache2k and provides the additional configuration data.
 *
 * @author Jens Wilke
 */
public class CacheConfigurationProviderImpl implements CacheConfigurationProvider {

  private final boolean usePullParser;

  {
    boolean _usePullParser = false;
    try {
      XmlPullParser.class.toString();
      _usePullParser = true;
    } catch (Exception ex) { }
    usePullParser = _usePullParser;
  }

  private ParsedConfiguration readManagerConfiguration(final CacheManager mgr) throws Exception {
    String _fileName = "/cache2k-" + mgr.getName() + ".xml";
    InputStream is = this.getClass().getResourceAsStream(_fileName);
    if (is == null) {
      return null;
    }
    ConfigurationTokenizer tkn;
    if (usePullParser) {
      tkn = new XppConfigTokenizer(_fileName, is, null);
    } else {
      tkn = new StaxConfigTokenizer(_fileName, is, null);
    }
    ParsedConfiguration cfg = ConfigurationParser.parse(tkn);
    VariableExpander _expander = new StandardVariableExpander();
    _expander.expand(cfg);
    return cfg;
  }

  @Override
  public Cache2kConfiguration getDefaultConfiguration(final CacheManager mgr) {
    try {
      ParsedConfiguration cfg = readManagerConfiguration(mgr);
      Cache2kConfiguration _bean = new Cache2kConfiguration();
      if (cfg != null) {
        ApplyConfiguration _apply = new ApplyConfiguration();
        _apply.apply(cfg.getSection("defaults").getSection("cache"), null, _bean);
      }
      return _bean;
    } catch (Exception ex) {
      throw new CacheMisconfigurationException(
        "Exception while processing default XML configuration for manager '" + mgr.getName() + "'", ex);
    }
  }

  @Override
  public <K, V> void augmentConfiguration(final CacheManager mgr, final Cache2kConfiguration<K, V> cfg) {

  }

}