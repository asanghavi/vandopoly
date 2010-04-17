/*****************************************************************************
 *   Copyright 2010 Vandopoly Team                                           *
 *   Licensed under the Apache License, Version 2.0 (the "License");         *
 *   you may not use this file except in compliance with the License.        *
 *   You may obtain a copy of the License at                                 *
 *                                                                           *
 *   http://www.apache.org/licenses/LICENSE-2.0                              *
 *                                                                           *
 *   Unless required by applicable law or agreed to in writing, software     *
 *   distributed under the License is distributed on an "AS IS" BASIS,       *
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.*
 *   See the License for the specific language governing permissions and     *
 *   limitations under the License.                                          *
 ****************************************************************************/

package org.vandopoly.messaging;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/*
 * EventCallback is a class that can store both an object and a method that needs to be updated
 * EventCallback is meant to be used with NotificationManager and is based off of Objective C's
 * iPhone callbacks.
 * 
 * @author James Kasten and Matt Gioia
 */
public class NetworkedEventCallback {

	private Object object_;
	private Method method_;
	private String eventName_;
	
	public NetworkedEventCallback(Object object, Method method, String eventName) {
		object_ = object;
		method_ = method;
		eventName_ = eventName;
	}

	public void notifyObserver(Object updatedObject) {
		try {
			if (method_.getParameterTypes().length == 1) {
				if(eventName_ == "")
					method_.invoke(object_, updatedObject);
				else
					method_.invoke(object_, updatedObject, eventName_);
			}
			else if (method_.getParameterTypes().length == 0) {
				if(eventName_ == "")
					method_.invoke(object_);
				else
					method_.invoke(object_, null, eventName_);
			}
			else
				System.err.println(this + " does not contain 0 or 1 parameter");
		} catch (IllegalAccessException e) {
			String error = "An IllegalAccessException has occured while trying to"
					+ "notify "	+ object_.getClass() + " with method " + method_.getName();
			System.err.println(error);
		} catch (InvocationTargetException e) {
			String error = "An InvocationTargetException has occured while trying to"
					+ "notify " + object_.getClass() + " with method " + method_.getName();
			System.err.println(error);
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			String error = "An IllegalArgumentException has occured while trying to"
					+ "notify " + object_.getClass() + " with method " + method_.getName()
					+ "\n Remember all methods that are updated are expected to have either"
					+ " an object parameter (the updated object) or no parameters at all";
			System.err.println(error);
		}
	}

	// Needed for the HashMap
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof EventCallback))
			return false;
		NetworkedEventCallback callback = (NetworkedEventCallback) obj;

		return object_.equals(callback.object_)
				&& method_.equals(callback.method_);
	}

	public Object getObject() {
		return object_;
	}

	public Method getMethod() {
		return method_;
	}

	public String toString() {
		return "Class: " + object_.getClass() + ", Method: "
				+ method_.getName();
	}
}
