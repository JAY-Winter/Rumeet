package com.d204.rumeet.ui.running

import android.util.Log
import com.d204.rumeet.domain.model.user.UserInfoDomainModel
import com.d204.rumeet.domain.onError
import com.d204.rumeet.domain.onSuccess
import com.d204.rumeet.domain.usecase.user.GetUserInfoUseCase
import com.d204.rumeet.ui.base.BaseViewModel
import com.d204.rumeet.ui.base.UiState
import com.d204.rumeet.ui.running.option.model.*
import com.d204.rumeet.util.amqp.RunningAMQPManager
import com.rabbitmq.client.AMQP
import com.rabbitmq.client.DefaultConsumer
import com.rabbitmq.client.Envelope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RunningViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase
) : BaseViewModel() {

    private val _runningSideEffect : MutableSharedFlow<RunningSideEffect> = MutableSharedFlow(replay = 1, extraBufferCapacity = 100)
    val runningSideEffect : SharedFlow<RunningSideEffect> get() = _runningSideEffect.asSharedFlow()

    val runningTypeModel = RunningTypeModel()

    fun startRun(userId : Int, roomId : Int){
        baseViewModelScope.launch {
            startRunningSubscribe(userId,roomId)
        }
    }

    fun getUserInfo(userId : Int){
        baseViewModelScope.launch {
            try {
                getUserInfoUseCase(userId)
                    .onSuccess { _runningSideEffect.emit(RunningSideEffect.SuccessUserInfo(it)) }
                    .onError { e -> catchError(e) }
            }catch (e : Exception){
                Log.e("TAG", "getUserInfo: catch ${e.message}", )
            }
        }
    }

    private fun startRunningSubscribe(userId : Int, roomId: Int){
        RunningAMQPManager.receiveRunning(roomId, userId, object : DefaultConsumer(RunningAMQPManager.runningChannel){
            override fun handleDelivery(
                consumerTag: String?,
                envelope: Envelope?,
                properties: AMQP.BasicProperties?,
                body: ByteArray
            ) {
                val distance = String(body)
                _runningSideEffect.tryEmit(RunningSideEffect.SuccessRunning(distance.toInt()))
            }
        })
    }

    // state = 1 싱글, state = 2 멀티
    fun setGameType(state: RunningType) {
        runningTypeModel.runningType = state
    }

    // 거리 or 협동의 난이도
    fun setDistance(distance : RunningDistance){
        runningTypeModel.distance = distance
    }

    // 누구와 달릴지
    fun setRunningDetailType(who : RunningDetailType){
        runningTypeModel.runningDetailType = who
    }

    // 난이도
    fun setRunningDifficulty(difficulty: RunningDifficulty){
        runningTypeModel.runningDifficulty = difficulty
    }
}