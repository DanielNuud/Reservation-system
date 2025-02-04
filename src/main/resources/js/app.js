import 'bootstrap';
import flatpickr from 'flatpickr';
import 'scss/app.scss';


/**
 * Register an event at the document for the specified selector,
 * so events are still catched after DOM changes.
 */
function handleEvent(eventType, selector, handler) {
  document.addEventListener(eventType, function(event) {
    if (event.target.matches(selector + ', ' + selector + ' *')) {
      handler.apply(event.target.closest(selector), arguments);
    }
  });
}

handleEvent('submit', '.js-submit-confirm', function(event) {
  if (!confirm(this.getAttribute('data-confirm-message'))) {
    event.preventDefault();
    return false;
  }
  return true;
});

handleEvent('change', '.js-selectlinks', function(event) {
  window.location = this.value;
});

function initDatepicker() {
  document.querySelectorAll('.js-datepicker, .js-timepicker, .js-datetimepicker').forEach(($item) => {
    const flatpickrConfig = {
      allowInput: true,
      time_24hr: true,
      enableSeconds: true
    };
    if ($item.classList.contains('js-datepicker')) {
      flatpickrConfig.dateFormat = 'Y-m-d';
    } else if ($item.classList.contains('js-timepicker')) {
      flatpickrConfig.enableTime = true;
      flatpickrConfig.noCalendar = true;
      flatpickrConfig.dateFormat = 'H:i:S';
    } else { // datetimepicker
      flatpickrConfig.enableTime = true;
      flatpickrConfig.altInput = true;
      flatpickrConfig.altFormat = 'Y-m-d H:i:S';
      flatpickrConfig.dateFormat = 'Y-m-dTH:i:S';
      // workaround label issue
      flatpickrConfig.onReady = function() {
        const id = this.input.id;
        this.input.id = null;
        this.altInput.id = id;
      };
    }
    flatpickr($item, flatpickrConfig);
  });
}
initDatepicker();

function fetchUpdatedHouses() {
  console.log("Fetching houses..."); // Проверь, появляется ли это в консоли
  fetch('/api/houses')
      .then(response => response.json())
      .then(houses => {
        console.log("Received houses:", houses);
        let houseList = document.getElementById('houses-list');
        if (!houseList) {
          console.error("Element with ID 'houses-list' not found!");
          return;
        }

        houseList.innerHTML = '';
        houses.forEach(house => {
          let houseCard = `
                <div class="col">
                    <div class="card mb-3" style="max-width: 100%; min-height: 120px;">
                        <div class="row g-0">
                            <div class="col-md-4">
                                <img src="${house.imagePaths[0]}" class="img-fluid rounded-start" alt="House Image">
                            </div>
                            <div class="col-md-8">
                                <div class="card-body d-flex flex-column">
                                    <h5 class="card-title">${house.name}</h5>
                                    <p class="card-text">${house.description}</p>
                                    <p class="card-text">
                                        <small class="text-muted">${house.area} m²</small>
                                    </p>
                                    <div class="mt-auto">
                                        <a href="/houses/${house.id}" class="btn btn-primary">Details</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>`;
          houseList.innerHTML += houseCard;
        });
      })
      .catch(error => console.error("Error fetching houses:", error));
}

setInterval(() => {
  console.log("Calling fetchUpdatedHouses()...");
  fetchUpdatedHouses();
}, 10000);
