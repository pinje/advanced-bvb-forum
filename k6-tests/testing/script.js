import http from 'k6/http';
import { sleep, check } from 'k6';

export const options = {
  stages: [
    { duration: '10s', target: 10 },
    { duration: '1m', target: 100 },
    { duration: '2m', target: 500 },
    { duration: '2m', target: 1000 },
    { duration: '3m', target: 5000 },
    { duration: '5m', target: 10000 },  // ramp up to 10000 users
    { duration: '5m', target: 10000 },  // stay at 10000 users
    { duration: '30s', target: 0 },  // ramp down to 0 users
  ],
  thresholds: {
    http_req_duration: ['p(95)<3000'], // 95% of requests should be below 3000ms
  }
};

function userTest() {
  const url = 'http://108.142.40.148/';

  const res = http.get(url);
  check(res, {
    'is status 200': (r) => r.status === 200,
    'response time < 3 seconds': (r) => r.timings.duration < 3000,
  });
}

function redirectionReview() {
  const url = 'http://108.142.40.148/create-review';

  const res = http.get(url);
  check(res, {
    'is status 200': (r) => r.status === 200,
    'response time < 3 seconds': (r) => r.timings.duration < 3000,
  });
}

// Default function
export default function () {
  userTest();
  sleep(30);
  redirectionReview();
  sleep(30);
  userTest();
  sleep(1);
}