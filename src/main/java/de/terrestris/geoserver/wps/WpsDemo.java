/* Copyright 2015 terrestris GmbH & Co. KG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.terrestris.geoserver.wps;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.geoserver.catalog.Catalog;
import org.geoserver.catalog.DataStoreInfo;
import org.geoserver.wps.gs.GeoServerProcess;
import org.geoserver.wps.process.RawData;
import org.geoserver.wps.process.StreamRawData;
import org.geotools.process.factory.DescribeParameter;
import org.geotools.process.factory.DescribeProcess;
import org.geotools.process.factory.DescribeResult;

/**
 *
 * @author Nils Bühner
 *
 */
@DescribeProcess(
	title = "wpsDemo",
	description = "WPS Demo"
)
public class WpsDemo implements GeoServerProcess {

	/**
	 * GS Catalog that will be injected from the GS-Spring-Context.
	 */
	private Catalog catalog;

	/**
	 * Constructor that accepts the catalog.
	 * (see applicationContext.xml)
	 * Used to inject the catalog from the GS.
	 *
	 * @param catalog
	 */
	public WpsDemo(Catalog catalog) {
		this.catalog = catalog;
	}

	@DescribeResult(
		name = "result",
		description = "The CSV result",
		// metadata for the result of the WPS
		// the first mimeType will be the default
		meta = {
			"mimeTypes=text/csv",
			"chosenMimeType=outputMimeType"
		}
	)
	public RawData execute(
		@DescribeParameter(
			name = "name",
			description = "The name to use in the result",
			min = 1,
			max = 1,
			meta = { "mimeTypes=text/plain" }
		) final String name,

		// This parameter will contain the output mime type the user has chosen.
		// It will NOT appear as a usual parameter in the DescribeProcess of the
		// WPS!
		@DescribeParameter(
			name = "outputMimeType",
			min = 0,
			max = 1
		) final String outputMimeType
	) {

		String result = "";

		List<DataStoreInfo> allStores = catalog.getDataStores();

		// according to the chosen mime type we will build the result
		if(outputMimeType.equals("text/csv")) {
			result =  "ID,name\n"
					+ "17," + allStores.size() + "\n"
					+ "42," + name;
		} else {
			result = name;
		}

		InputStream stringResultInputStream = new ByteArrayInputStream(result.getBytes(StandardCharsets.UTF_8));

		return new StreamRawData(outputMimeType, stringResultInputStream, "csv");
	}

	/**
	 * @return the catalog
	 */
	public Catalog getCatalog() {
		return catalog;
	}

	/**
	 * @param catalog the catalog to set
	 */
	public void setCatalog(Catalog catalog) {
		this.catalog = catalog;
	}
}
