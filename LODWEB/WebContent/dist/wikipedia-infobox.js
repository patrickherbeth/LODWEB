$.ajax({
  dataType: 'jsonp', // no CORS
  url: 'https://en.wikipedia.org/w/api.php',
  data: {
    action: 'query',
    prop: 'revisions',
    rvprop: 'content',
    format: 'json',
    rvsection: '0', // infobox
    rvparse: '', // convert to HTML
    redirects: '', // follow title redirects
    titles: title
  },
  success: function(data) {
    var keys = Object.keys(data.query.pages);
    var content = data.query.pages[keys[0]].revisions[0]['*'];
    var data = $('.infobox', content).get(0);
  }
});