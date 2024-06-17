import http from 'k6/http';
import { sleep, check } from 'k6';

export const options = {
    stages: [
        { duration: '10s', target: 10 },
        { duration: '1m', target: 100 },
        { duration: '2m', target: 500 },
        { duration: '2m', target: 1000 },
        { duration: '30s', target: 0 },  
      ],
    thresholds: {
        http_req_duration: ['p(95)<300'], // 95% of requests should be below 300ms
    }
};

function seasonTest() {
    const url = 'http://57.153.111.182:8222/api/v1/season';
    const jwtToken = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6IjIiLCJ1c2VybmFtZSI6ImFkbWluIiwic3ViIjoiYWRtaW4iLCJpYXQiOjE3MTg0NTE5NzUsImV4cCI6MTcxODUzODM3NSwiYXV0aG9yaXRpZXMiOlsiQURNSU4iXX0.FKNzIimI0nGQmcAjgHQ1H88NUOg0gEq9psM7-DK6QXM";

    const params = {
        cookies: {
            'jwtToken': jwtToken
        },
    };

    const res = http.get(url, params);
    check(res, {
        'is status 200': (r) => r.status === 200,
        'response time < 300ms': (r) => r.timings.duration < 300,
    });
};

// Default function
export default function () {
    seasonTest();
    sleep(1);
}