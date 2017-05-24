/**
 *  Copyright 2015 SmartThings
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 *  Turn It On For 5 Minutes
 *  Turn on a switch when a contact sensor opens and then turn it back off 5 minutes later.
 *
 *  Author: SmartThings
 */
definition(
    name: "Turn It On For n Minutes",
    namespace: "johnypony3",
    author: "johnypony3",
    description: "When a switch is turned on, it will be turned on by the set number of minutes. Defaults to 15",
    category: "Green Living",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Meta/light_contact-outlet.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Meta/light_contact-outlet@2x.png"
)

preferences {
    section("Which switch?") {
		    input "switches", "capability.switch", title: "Switches",  multiple: true, required: false
        input "bulbs", "capability.bulb", title: "Bulbs",  multiple: true, required: false
        input "lights", "capability.light", title: "Lights",  multiple: true, required: false
    }
    section("How many minutes?") {
        input "minutes", "number", required: true, title: "How many minutes?", defaultValue: 15
    }
}

def subscribe() {
  if (switches){
    subscribe(switches, "switch.on", on_Handler)
    //subscribe(switches, "switch.off", off_Handler)
  }

  if (bulbs){
    subscribe(bulbs, "bulb.on", on_Handler)
    //subscribe(bulbs, "bulb.off", off_Handler)
  }

  if (lights){
    subscribe(lights, "light.on", on_Handler)
    //subscribe(lights, "light.off", off_Handler)
  }
}

def installed() {
	log.debug "Installed with settings: ${settings}"
  subscribe()
}

def updated(settings) {
	log.debug "Updated with settings: ${settings}"
	unsubscribe()
  subscribe()
}

def on_Handler(evt) {
    String deviceId = evt.deviceId
    log.debug "[on_Handler] The device id for this event: ${deviceId}"

    device = evt.device
    device.on()
  	def delay = 5//30 * settings.minutes
  	//runIn (delay, device_off, [data: [deviceId: deviceId]])
    runIn(delay, device_off, [data: [deviceId: deviceId]])
}

def off_Handler(evt) {
    String deviceId = evt.deviceId
    log.debug "[off_Handler] The device id for this event: ${deviceId}"
    /*
    device = evt.device
    device.off()
    runIn (delay, device.off(), [overwrite: false])
    */
}

def device_off(data) {
    String deviceId = data.deviceId
    log.debug "[off_Handler] The device id for this event: ${deviceId}"

    def devices = settings.inputDevices

    devices.findAll( { it.id == deviceId} ).each {
      if (it){
        log.debug "Found device: ID: ${it.id}, Label: ${it.label}, Name: ${it.name}"
        it.off()
      }
    }
}
