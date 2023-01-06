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

import {ServiceUtil} from "./service-util";
import {HttpErrorResponse} from "@angular/common/http";

describe('ServiceUtil', () => {
  it('should handle network error', () => {
    ServiceUtil.handleError(<HttpErrorResponse>{status: 0})
      .subscribe({
        next: () => fail('error expected'),
        error: error => expect(error.message).toEqual('A client-side or network error occurred; please try again later.')
      });
  });
  it('should handle server error', () => {
    const error = <HttpErrorResponse>{status: 404, error: 'this does not work'};
    ServiceUtil.handleError(error)
      .subscribe({
        next: () => fail('error expected'),
        error: error => expect(error.message).toEqual(`Server error ${error.status}: ${error.error}; please try again later.`)
      });
  });
});
