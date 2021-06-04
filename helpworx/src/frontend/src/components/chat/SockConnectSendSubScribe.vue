<template>
    <v-row justify="center" class="mt-6" style="margin-left: 900px">
        <v-dialog v-model="dialog" persistent scrollable max-width="680" max-height="950">
            <template v-slot:activator="{ on, attrs }">
                <v-btn color="teal" dark v-bind="attrs" large v-on="on" rounded> <v-icon large>mdi-forum</v-icon>문의하기 </v-btn>
            </template>

            <v-card>
                <v-card height="30">
                    <v-btn color="green darken-1" text @click="closeDialog">닫기</v-btn>
                    <p style="float:right;margin-top:5px; width:500px font-weight:bold">나의 진행중인 실시간 문의</p>
                </v-card>

                <v-card-text style="height: 850px; margin-left:-15px">
                    <v-card elevation="0" id="chat-page" class="hidden">
                        <v-card elevation="0" height="750" class="scroll" width="100%" id="convrBox">
                            <v-card v-if="isConnected == false && isConnecting == false && chttRoomList != null">
                                <v-card
                                    style="margin:20px; padding-left:10px; padding-top:15px"
                                    elevation="0"
                                    color="#c5dcc2"
                                    v-for="(chttRoom, index) in chttRoomList"
                                    @click="existedConnect(chttRoom)"
                                    :key="index"
                                >
                                    <span v-if="chttRoom.chttList != null && chttRoom.chttList.length > 0">
                                        <p class="chttInfo">{{chttRoom.chttList[0].user.userNm}} 님의 {{ chttRoom.sysNm }} 에 대한 상담</p>
                                        <p></p>
                                        <p class="chttInfo" style="float:left;">마지막 채팅 |</p>
                                        <p>{{ chttRoom.chttList[chttRoom.chttList.length - 1].chttCreatDate }}</p>
                                        <p>{{ chttRoom.chttList[chttRoom.chttList.length - 1].convrs }}</p>
                                        <br />
                                    </span>
                                </v-card>
                            </v-card>

                            <ul v-if="isConnected == true && this.chttRoom != null" id="messageArea">
                                <li v-for="chtt in this.chttRoom.chttList" :key="chtt.id" class="message">
                                    <i v-if="chtt.user.userNm == userData.userNm" style="background-color:yellow;"> 나</i>
                                    <i v-else style="background-color:lightgrey;"> {{ chttRoom.sysNm }} 팀 </i>
                                    {{ chtt.chttCreatDate.substring(11, 20) }}
                                    <p>{{ chtt.convrs }}</p>
                                </li>
                            </ul>
                            <v-card elevation="0" id="systemSelectBox" v-if="isConnecting == false && isConnected == true && chttRoom != null && chttRoom.sysNm == null && systems != null">
                                <h2>문의하실 시스템을 선택해주세요</h2>
                                <v-btn v-for="sys in systems" :key="sys.sysNm" :value="sys.sysNm" @click="selectSystem(sys.sysNm)">{{ sys.sysNm }}</v-btn>
                            </v-card>
                            <v-card color="#5CB85C" elevation="0" v-if="isConnecting == true">
                                <v-card-text>연결중 . . .</v-card-text>
                            </v-card>
                        </v-card>

                        <v-card elevation="0" v-if="isConnected == true && chttRoom != null && chttRoom.sysNm != null && isConnecting == false">
                            <v-text-field
                                ref="sendMessageArea"
                                @keyup.enter="sendMessage"
                                type="text"
                                v-model="message"
                                placeholder="Type a message..."
                                autocomplete="off"
                                dense
                                hide-details
                            ></v-text-field>
                            <v-btn x-small @click="sendMessage">보내기</v-btn>
                        </v-card>

                        <v-card elevation="0" v-if="isConnected == false && isConnecting == false">
                            <v-btn small @click="newConnect">{{ this.userData.userNm }} 님 새 대화 시작하기</v-btn>
                        </v-card>
                    </v-card>
                </v-card-text>
            </v-card>
        </v-dialog>
    </v-row>
</template>

<script>
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";
import "vue-datetime/dist/vue-datetime.css";
import { getCtmmnySys, updateChttroomsById, getchttrooms, createChttrooms } from "@/api/index";

var colors = ["#2196F3", "#32c787", "#00BCD4", "#ff5652", "#ffc107", "#ff85af", "#FF9800", "#39bbb0"];

const getYYYYMMDDHHMMSS = function(time) {
    const format = "YYYY-MM-DD HH:mm:ss";
    return time.format(format);
};
var socket;
var stompClient;

export default {
    props: ["userData"],
    data() {
        return {
            username: "",
            isConnected: false,
            isConnecting: false,
            message: "",
            dialog: false,
            chttRoomList: null,
            chttRoom: null,
            systems: null,
        };
    },

    created: function() {
        this.getchttRoomList();
        this.getCtmmnySys();
    },
    methods: {
        async getCtmmnySys() {
            const res = await getCtmmnySys.getRes();
            this.systems = res.data;
        },
        closeDialog() {
            this.dialog = false;
            this.isConnected = false;
            if (stompClient != undefined) {
                stompClient.disconnect();
            }
        },

        getNow() {
            return new Date(getYYYYMMDDHHMMSS(this.$moment(new Date()))).getTime();
        },
        async selectSystem(sysNm) {
            var chtt = {
                convrs: "어서오세요 " + sysNm + " 에 대한 상담을 시작합니다.",
                chttCreatDate: this.getNow(),
                chttRoom: this.chttRoom,
                convrsType: "CHAT",
                user: { id: 11111 }, //운영자 아이디로 변경해주기
            };
            await stompClient.send("/app/chat.sendMessage", JSON.stringify(chtt));
            this.message = "";
            this.chttRoom.sysNm = sysNm;
            let requestBody = JSON.stringify(this.chttRoom);
            const res = await updateChttroomsById.getRes(requestBody, this.chttRoom.id);
            console.log(this.chttroom);
            this.chttRoom = res.data;
        },
        async getchttRoomList() {
            const res = await getchttrooms.getRes();
            this.chttRoomList = res.data;
        },
        async newConnect() {
            socket = new SockJS("/ws");
            stompClient = Stomp.over(socket);

            this.isConnecting = true;
            if (this.userData != null) {
                this.isConnected = true;

                var ChttRoom = {
                    chttStartTim: this.getNow(),
                    status: "진행중",
                    chttList: [],
                };
                var requestBody = JSON.stringify(ChttRoom);
                const res = await createChttrooms.getRes(requestBody);
                this.chttRoom = res.data;
                await stompClient.connect({}, this.sendInitMessage, this.onError);
            }
        },

        async sendInitMessage() {
            var chtt = { user: { id: this.userData.userId }, chttCreatDate: this.getNow(), convrs: this.userData.userNm + " 님이 입장했어요!", convrsType: "JOIN", chttRoom: this.chttRoom };

            await stompClient.subscribe(`/topic/public/${this.chttRoom.id}`, this.onMessageReceived, this.onError); //연결되면 구독하기.
            this.isConnected = true;
            this.isConnecting = false;

            await stompClient.send("/app/chat.sendMessage", JSON.stringify(chtt));
        },

        async existedConnect(chttRoom) {
            socket = new SockJS("/ws");
            stompClient = Stomp.over(socket);
            this.chttRoom = chttRoom;
            this.isConnecting = true;
            this.isConnected = true;
            if (this.userData != null) {
                await stompClient.connect({}, this.onExistedConnected, (error) => {
                    console.log(error);
                });
            }
        },

        async onExistedConnected() {
            await stompClient.subscribe(`/topic/public/${this.chttRoom.id}`, this.onMessageReceived, this.onError); //연결되면 구독하기.
            this.isConnecting = false;
            this.isConnected = true;
        },

        onError: function(error) {
            this.isConnecting = false;
            this.isConnected = false;
            alert("에러", error);
        },
        sendMessage() {
            this.message = this.message.trim();
            if (this.message != "" && stompClient) {
                var chtt = {
                    convrs: this.message,
                    chttCreatDate: this.getNow(),
                    chttRoom: this.chttRoom,
                    convrsType: "CHAT",
                    user: { id: this.userData.userId },
                };

                stompClient.send("/app/chat.sendMessage", JSON.stringify(chtt));
                this.message = "";
            }
            this.$refs["sendMessageArea"].$refs.input.focus();
        },
        onMessageReceived(res) {
            this.chttRoom.chttList.push(JSON.parse(res.body));
            var messageArea = document.querySelector("#messageArea");
            var convrBox = document.querySelector("#convrBox");
            messageArea.scrollTop = messageArea.scrollHeight;
            convrBox.scrollTop = convrBox.scrollHeight;
        },

        getAvatarColor(messageUserName) {
            var hash = 0;
            for (var i = 0; i < messageUserName.length; i++) {
                hash = 31 * hash + messageUserName.charCodeAt(i);
            }
            var index = Math.abs(hash % colors.length);
            return colors[index];
        },
    },
};
</script>

<style scopped>
p.chttInfo {
    /* style="font-color:red; font-weight:bold; font-size:1.1em" */
    font-color: red;
    font-weight: bold;
    font-size: 1.2em;
}

i {
    padding: 3px;
}

.scroll {
    overflow-y: scroll;
    margin-top: 10px;
    width: 650px;
}

.message {
    border-bottom: solid 1px rgba(37, 85, 100, 0.411);
    margin-left: -33px;
    padding-left: 13px;
    padding-top: 15px;
}
.message p {
    padding: 5px;
    padding-top: 10px;
}

.v-btn__content .v-btn:not(.v-btn--round).v-size--default {
    width: 10px;
    height: 10px;
    position: absolute;
    right: 0;
}
</style>
