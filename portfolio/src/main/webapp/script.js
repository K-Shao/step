// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * Adds a random greeting to the page.
 */
function addRandomGreeting() {
  const greetings =
      ['Custard, good. Jam, good. Meat, good.', 
        'Joey doesn\'t share food!', 
        'I don\'t like it when people take food off of my plate, okay?', 
        'The fridge broke so I had to eat everything', 
        'That\'s a great story. Can I eat it?'];

  // Pick a random greeting.
  const greeting = greetings[Math.floor(Math.random() * greetings.length)];

  // Add it to the page.
  const greetingContainer = document.getElementById('greeting-container');
  greetingContainer.innerText = greeting;
}

function toggleVisibility (sectionToToggle) {
  const invisibleClass = 'invisible';
  const toggleDiv = document.getElementById(sectionToToggle);
  const otherDiv = document.getElementById(sectionToToggle === 'swimming' ? 'rowing': 'swimming');
  if (toggleDiv.classList.contains(invisibleClass)) { 
    //if toggleDiv is invisible, toggleDiv becomes visible, all others become invisible
    toggleDiv.classList.remove(invisibleClass)
    otherDiv.classList.add(invisibleClass);
  } else { 
    //if toggleDiv is visible, toggleDiv becomes invisible
    toggleDiv.classList.add(invisibleClass)
  }
}

async function fetchComments() {
  const response = await fetch('/data');
  const comments = await response.json();
  console.log(comments);
  const commentsSection = document.getElementById('comments');
  commentsSection.innerHTML = '';
  for (var i = 0; i < comments.length; i++) {
    commentsSection.innerHTML += "<p>";
    commentsSection.innerHTML += comments[i];
    commentsSection.innerHTML += "</p>";
  }
}