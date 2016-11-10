import * as moment from 'moment';

export function getUID() {
  return Math.random().toString(36).substr(2, 9);
}

export function formatDate(date:string) {
  return moment(date, 'MMM DD, YYYY hh:mm').format('MMMM YYYY');
}
