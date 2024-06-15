import http from 'k6/http';
import { sleep, check } from 'k6';

export const options = {
  stages: [
    { duration: '10s', target: 10 },
    { duration: '1m', target: 100 },
    { duration: '2m', target: 500 },
    { duration: '2m', target: 1000 },
    { duration: '3m', target: 5000 }, 
    { duration: '5m', target: 10000 },
    { duration: '5m', target: 10000 },   
    { duration: '30s', target: 0 }
  ],
  thresholds: {
    http_req_duration: ['p(95)<3000'], // 95% of requests should be below 300ms
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

function redirectionLogin() {
      const url = 'http://108.142.40.148/login';
    
      const res = http.get(url);
      check(res, {
        'is status 200': (r) => r.status === 200,
        'response time < 3 seconds': (r) => r.timings.duration < 3000,
      });
}

function loginTest() {
  const login = {
    email: "admin@gmail.com",
    password: "12345678"
  };
  const url = 'http://57.153.111.182:8222/api/v1/auth/login';
  const headers = { 'Content-Type': 'application/json' };

  const res = http.post(url, JSON.stringify(login), { headers: headers });
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

function createReview() {
    const review = {
        "matchId": 1,
        "review": "this is a test review."
    };
      const url = 'http://57.153.111.182:8222/api/v1/post/protected';
      const headers = { 'Content-Type': 'application/json' };
    
      const res = http.post(url, JSON.stringify(review), { headers: headers });
      check(res, {
        'is status 200': (r) => r.status === 200,
        'response time < 3 seconds': (r) => r.timings.duration < 3000,
      });
}

// Default function
export default function () {
  userTest();
  sleep(1);
  redirectionLogin();
  sleep(1);
  loginTest();
  sleep(30);
  redirectionReview();
  sleep(30);
  createReview();
  sleep(1);
}