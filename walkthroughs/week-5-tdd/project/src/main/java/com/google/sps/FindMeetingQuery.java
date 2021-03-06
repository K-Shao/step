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

package com.google.sps;

import static com.google.common.collect.ImmutableList.toImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Streams;

public final class FindMeetingQuery {

  private Collection<TimeRange> getValidRanges(Collection<TimeRange> invalidTimes, long duration) {
    int currentTime = TimeRange.START_OF_DAY;
    List<TimeRange> validRanges = new ArrayList<TimeRange>();
    for (TimeRange time : invalidTimes) {
      if (time.start() >= currentTime + duration) {
        validRanges.add(TimeRange.fromStartEnd(currentTime, time.start(), false));
      }
      if (time.end() > currentTime) {
        currentTime = time.end();
      }
    }
    if (currentTime + duration <= TimeRange.END_OF_DAY) {
        validRanges.add(TimeRange.fromStartEnd(currentTime, TimeRange.END_OF_DAY, true));
    }
    return validRanges;
  }

  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    ImmutableList<TimeRange> invalidTimesMandatory = 
        Streams.stream(events)
        .filter(event -> !Collections.disjoint(event.getAttendees(), request.getAttendees()))
        .map(Event::getWhen)
        .sorted(TimeRange.ORDER_BY_START)
        .collect(toImmutableList());

    ImmutableList<TimeRange> invalidTimesOptional = 
        Streams.stream(events)
        .filter(event -> !Collections.disjoint(event.getAttendees(), request.getAttendees())
            || !Collections.disjoint(event.getAttendees(), request.getOptionalAttendees()))
        .map(Event::getWhen)
        .sorted(TimeRange.ORDER_BY_START)
        .collect(toImmutableList());

    Collection<TimeRange> rangeWithOptional = 
        getValidRanges(invalidTimesOptional, request.getDuration());

    if (request.getAttendees().isEmpty()) {
      return rangeWithOptional;
    }
    return rangeWithOptional.isEmpty() ? 
        getValidRanges(invalidTimesMandatory, request.getDuration()) : rangeWithOptional;

  }
}
