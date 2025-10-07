        
var meetups = [
  {slug: "BreizhJUG", title: "BreizhJUG"},
  {slug: "Docker-Rennes", title: "Docker Rennes"},
  {slug: "RennesJS", title: "RennesJS"},
  {slug: "Golang-Rennes", title: "Golang Rennes"},
  {slug: "Software-Craftsmanship-Rennes", title: "Software Crafters Rennes"},
  {slug: "rennes-devops", title: "Rennes devops"},
  {slug: "Agile-Rennes", title: "Agile Rennes"},
  {slug: "AFUP-Rennes", title: "AFUP Rennes"},
  {slug: "ecoconception-de-services-numeriques-rennes", title: "Écoconception de services numériques Rennes"},
  {slug: "Flutter-Rennes", title: "Mobile Rennes"},
  {slug: "python-rennes", title: "Python Rennes"},
];


$.each(meetups, function(_index, meetup) {
  $("#icons").append(
    "<div class='icon'><a href='https://www.meetup.com/" + meetup.slug + "/'>"
    + "<img src='meetups/" + meetup.slug + ".png'/ title='" + meetup.title + "'></a></div>"
  );
});