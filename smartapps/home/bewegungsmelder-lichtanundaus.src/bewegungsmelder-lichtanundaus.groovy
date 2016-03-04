/**
 *  BewegungsMelder_LichtAnUndAus
 *
 *  Copyright 2016 Reinhard Stoiber
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
 */
definition(
    name: "BewegungsMelder_LichtAnUndAus",
    namespace: "Home",
    author: "Reinhard Stoiber",
    description: "Sobald Bewegung detektiert wird, schaltet sich die Philips Hue Glühbirne Namens Abstellkammer ein. Wenn keine Bewegung mehr detektiert wird, schaltet sich diese wieder aus.",
    category: "My Apps",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


preferences {
    section("Turn on when motion detected:") {
        input "themotion", "capability.motionSensor", required: true, title: "Where?"
    }
    section("Turn on this light") {
        input "theswitch", "capability.switch", required: true
    }
}

def installed() {
	log.debug "Installed with settings: ${settings}"

	initialize()
}

def updated() {
	log.debug "Updated with settings: ${settings}"

	unsubscribe()
	initialize()
}

def initialize() {
    subscribe(themotion, "motion.active", motionDetectedHandler)
    subscribe(themotion, "motion.inactive", motionVanishedHandler)
}

def motionDetectedHandler(evt) {
    log.debug "motionDetectedHandler called: $evt"
    theswitch.on()
}

def motionVanishedHandler(evt) {
    log.debug "motionVanishedHandler called: $evt"
    theswitch.off()
}

