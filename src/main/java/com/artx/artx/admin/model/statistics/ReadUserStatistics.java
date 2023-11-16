package com.artx.artx.admin.model.statistics;

import com.artx.artx.user.type.UserRole;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.Objects;

public class ReadUserStatistics {

	@Getter
	@Builder
	public static class Response {
		private long newUserCounts;
		private long newArtistCounts;

		public static Response from(Map<UserRole, Long> map){

			return Response.builder()
					.newUserCounts(Objects.isNull(map.get(UserRole.USER)) ? 0 : map.get(UserRole.USER))
					.newArtistCounts(Objects.isNull(map.get(UserRole.ARTIST)) ? 0 : map.get(UserRole.ARTIST))
					.build();
		}
	}
}
