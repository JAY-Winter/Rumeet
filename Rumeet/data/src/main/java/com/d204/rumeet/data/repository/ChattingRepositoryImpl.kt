package com.d204.rumeet.data.repository

import com.d204.rumeet.data.remote.api.ChattingApiService
import com.d204.rumeet.data.remote.api.handleApi
import com.d204.rumeet.data.remote.dto.response.chatting.ChattingResponseDto
import com.d204.rumeet.data.remote.dto.response.chatting.ChattingRoomResponseDto
import com.d204.rumeet.data.remote.dto.response.chatting.toDomainModel
import com.d204.rumeet.domain.NetworkResult
import com.d204.rumeet.domain.model.chatting.ChattingModel
import com.d204.rumeet.domain.model.chatting.ChattingRoomModel
import com.d204.rumeet.domain.repository.ChattingRepository
import com.d204.rumeet.domain.toDomainResult
import javax.inject.Inject

internal class ChattingRepositoryImpl @Inject constructor(
    private val chattingApiService: ChattingApiService
) : ChattingRepository {
    override suspend fun getChattingRooms(userId: Int): NetworkResult<List<ChattingRoomModel>> {
        return handleApi { chattingApiService.getChattingRoom(2) }
            .toDomainResult<List<ChattingRoomResponseDto>, List<ChattingRoomModel>> { response ->
                response.map {
                    it.toDomainModel()
                }
            }
    }

    override suspend fun getChattingInfo(roomId: Int): NetworkResult<ChattingModel> {
        return handleApi { chattingApiService.getChattingList(roomId) }.toDomainResult<ChattingResponseDto, ChattingModel> { it.toDomainModel() }
    }
}