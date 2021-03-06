/**
 * Copyright (c) 2016 Couchbase, Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package com.couchbase.lite;

import java.util.ArrayList;
import java.util.List;

/**
 * This uses system api (on Android, uses the Context) to listen for network reachability
 * change events and notifies all NetworkReachabilityListeners that have registered themselves.
 * (an example of a NetworkReachabilityListeners is a Replicator that wants to pause when
 * it's been detected that the network is not reachable)
 */
public abstract class NetworkReachabilityManager {

    protected List<NetworkReachabilityListener> listeners =
            new ArrayList<NetworkReachabilityListener>();

    /**
     * Add Network Reachability Listener
     */
    public synchronized void addNetworkReachabilityListener(NetworkReachabilityListener listener) {
        int numListenersBeforeAdd = listeners.size();
        listeners.add(listener);
        if (numListenersBeforeAdd == 0)
            startListening();
    }

    /**
     * Remove Network Reachability Listener
     */
    public synchronized void removeNetworkReachabilityListener(NetworkReachabilityListener listener) {
        listeners.remove(listener);
        if (listeners.size() == 0)
            stopListening();
    }

    /**
     * Notify listeners that the network is now reachable
     */
    public synchronized void notifyListenersNetworkReachable() {
        for (NetworkReachabilityListener listener : listeners)
            listener.networkReachable();
    }

    /**
     * Notify listeners that the network is now unreachable
     */
    public synchronized void notifyListenersNetworkUneachable() {
        for (NetworkReachabilityListener listener : listeners)
            listener.networkUnreachable();
    }

    /**
     * This method starts listening for network connectivity state changes.
     */
    public abstract void startListening();

    /**
     * This method stops this class from listening for network changes.
     */
    public abstract void stopListening();

    public abstract boolean isOnline();
}