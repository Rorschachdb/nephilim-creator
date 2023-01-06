/*
 * nephilim.online.creator.back
 *
 * Copyright (c) 2023 by rorshach-corp
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

import {HttpErrorResponse} from "@angular/common/http";
import {throwError} from "rxjs";

/**
 * Service utility function
 */
export const ServiceUtil = {

  /**
   * Basic and common error management
   * @param error
   */
  handleError: (error: HttpErrorResponse) => {
    let message: string;
    if (error.status === 0) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error);
      message = 'A client-side or network error occurred; please try again later.';

    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong.
      console.error(
        `Server error ${error.status}: `, error.error);
      message = `Server error ${error.status}: ${error.error}; please try again later.`;
    }
    // Return an observable with a user-facing error message.
    return throwError(() => new Error(message));
  }
}
