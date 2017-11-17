🚧 This project is still a WIP 🚧
# Dawson Student App 2017
For all specs and information about this project, please consult our [Wiki](https://github.com/sirMerr/dawson-student-app/wiki) or go [here](https://sirmerr.github.io/compsci-notes/Mobile-Development/assignments/final-assignment/overview/)!

## Contributors
[![Tiffany Le-Nguyen](https://s.gravatar.com/avatar/5ff6912552de8e7e2770b7c7005e62f2?s=400)](https://github.com/sirMerr) | [![Marc Daniels](https://avatars3.githubusercontent.com/u/26173807?s=400&v=4)](https://github.com/marcdaniels48) | [![Trevor Eames](https://avatars0.githubusercontent.com/u/8592679?s=400&v=4)](https://github.com/gimongi) | [![Roan Chamberlain](https://avatars1.githubusercontent.com/u/14024438?s=400&v=4)](https://github.com/roanwc)
---|---|---|---|
[Tiffany Le-Nguyen](https://github.com/sirMerr) | [Marc-Daniel Dialogo](https://github.com/imbaky) | [Trevor Eames](https://github.com/naz321)| [Roan Chamberlain](https://github.com/roanwc)

## Overview
This app is intended to be useful for new and current Dawson students. The purpose of this project is to create an application that will exercise what you have learned about the Android framework  and Mobile software development. It is to be integrated with your final project for PHP. In phase 1 you will implement most app only components, in phase 2 you will integrate with your PHP and PostgresSL server side data.    
## General functionality
On first launch the app will have the user input their `name`, an `email address` as `userid`, and a `password`.   **Do not use a splash page for this**, check and if there is no remembered information (`SharedPrefs`) launch an `Activity` to collect this information.   Once this is input the `MainActivity` displays a menu and series of buttons per the UI specification  to invoke various functionality. On button click most will launch an activity, maybe containing fragments, the design of the UIs is up to you but make it clean and neat, aligned and looking nice.  If you want to do something I have not requested or use some additional Android classes (example: Material Design) that I have not covered please check with me first.  I am open to agile-type design decisions but you must meet the spec .  **I am your Project Manager,  you must report to me for anything that is outside the spec.**

## On Startup
* 1st launch: ask for last name, first name, email address and password  save it in shared preferences with date stamp,  **(phase 2 will involve checking the back end web REST resources from the PHP project for updates and downloading data, using this information)**
* Subsequent launch you will not ask for authentication, the auth data will be used for access to the back end.  

## General
- [ ] Unique App Name: Dawson<TeamName>
- [ ] Unique repo name: must include TeamName
- [ ] Unique Launcher icon, (you may make your own) http://romannurik.github.io/AndroidAssetStudio/
- [ ] Include appropriate inline documentation and JavaDocs for your methods and classes.
- [ ] All images should be scalable (png, or jpg) and must resize on rotate
- [ ] All UI strings and integers must be resources, no constants.
- [ ] All code-internal data must be constants, no literals, even for logging.
- [ ] Use logging with a unique tag for each Activity/Fragment
- [ ] You are required to use Fragments in at least one UI
- [ ] Maintain state on rotation and wherever necessary for all UIs

## Grading  

**NOTE: Same as the PHP side of the project**

Part of your grade will reflect how you are able to organize your work and advance during the time that has been allocated. You must be able to show progress (procrastinators will be penalized). **Please be prepared to attend your scheduled lab session for spot checks.**

You must use the protected branch workflow as in the assignments but you will be 4 now so it changes the game a little.   The penultimate Monday you will be expected to demo the full android progject.   We will also organize combined demonstrations on the last Monday of the semester (with the PHP project). These are demos are scheduled before the final project due date. This means that:

* you may still be missing some functionality (but not a lot) 
* Your UI should be finalized, the underlying code must be as complete as possible
* you must be prepared to explain what is remaining to do or fix (it should not be much at this point in time)

## Phase 1 (to start)
- [ ] Choose a team name, check it with the teacher
- [ ] Create a private, shared repository on gitlab or github with all team members as contributors add campbe13,  I will be checking it sporadically.  You may want to protect your master branch to avoid pushing errors. I suggest you designate one person as the github controller.
- [ ] Design your UX see the UI Layout
- [ ] Create / gather / modify the necessary images for your main activity UI and the About page.
- [ ] Create the UI associated resources and code that launches stub activities for all buttons you will implement.
- [ ] Create your firebase database using the phonebook json file. 
- [ ] Create your SQLite database DAO. 
- [ ] Implement  startup check for shared preferences, input user info.
- [ ] Implement the Menu 
- [ ] For the Menu & UI implement stub Activities then focus on one at a time of the following:
    - [ ] Implement the About Activity
    - [ ] Implement the Settings Activity
    - [ ] Implement the Activites/Fragments  associated with the actions  for phase 1in the main UI  (buttons or  …)
    - [ ] Implement the main UI have each component launch a stub Activity

The preceeding is how to setup the skeleton for phase 1.   Once you have the skeleton ready distribute the work and implement the details in [Phase 1 Specification](https://docs.google.com/document/d/1Mhg5NBIdvHkn-E65nneWjMOgvw2sBuWgCcllvIOy9Lc/edit)

We may not use all of the following apis, you may delay getting keys until you need them.
- [ ] Sign up for an API key for Open Weather Map https://openweathermap.org/api
- [ ] Sign up for an API key for Google Places  https://developers.google.com/places/android-api/start  
    - [ ] Use PlacePicker https://developers.google.com/places/android-api/placepicker
    - [ ] Use CurrentPlace https://developers.google.com/places/android-api/current-place

For all specs and more detailed information, check the [Wiki](https://github.com/sirMerr/dawson-student-app/wiki) or go [here](https://sirmerr.github.io/compsci-notes/Mobile-Development/assignments/final-assignment/overview/)