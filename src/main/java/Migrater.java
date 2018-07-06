/*
 * Copyright (c) 2008-2018, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.config.Config;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.test.starter.HazelcastStarter;

public class Migrater {

    public static void main(String[] args) {
        Config config = new Config();
        config.getGroupConfig().setName("old");
        HazelcastInstance instance = HazelcastStarter.newHazelcastInstance("3.2", config, false);
        ClientConfig clientConfig = new ClientConfig();

        clientConfig.getGroupConfig().setName("new");
        clientConfig.getNetworkConfig().addAddress("10.216.1.47:5702");
        HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);

        IMap<Object, Object> map = instance.getMap("map");
        IMap<Object, Object> newMap = client.getMap("map");
        newMap.putAll(map.getAll(map.keySet()));
    }
}
