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

import org.geotools.process.factory.DescribeParameter;
import org.geotools.process.factory.DescribeProcess;
import org.geotools.process.factory.DescribeResult;

import org.geoserver.wps.gs.GeoServerProcess;

/**
 * 
 * A simple hello world WPS example.
 * 
 * @author Marc Jansen
 * @author Nils Bühner
 *
 */
@DescribeProcess(
	title = "helloWorldWps",
	description = "Hello World WPS Example"
)
public class HelloWorldWps implements GeoServerProcess {

	@DescribeResult(
		name = "result",
		description = "The Hello world string"
	)
	public String execute(
			@DescribeParameter(
				name = "name",
				description = "The name to use in the result"
			) String name) {
		return "Hello, " + name + "!";
	}
}
