package com.ssafy.basic.trip;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@DisplayName("여행지 조회 테스트")
public class SearchTest {

    @Test
    @DisplayName("시도 조회")
    void 시도를_조회한다() {
        List<Sido> sidoList = new ArrayList<>();
        Sido seoul = new Sido(1, "seoul");
        Sido jeonju = new Sido(2, "jeonju");
        Sido busan = new Sido(3, "busan");
        sidoList.add(seoul);
        sidoList.add(jeonju);
        sidoList.add(busan);
        assertThat(sidoList.size()).isEqualTo(3);


    }

    @Test
    @DisplayName("시도에 속한 구군 조회")
    void 시도에_속한_구군을_조회한다() {
        Sido seoul = new Sido(1, "seoul");
        List<Gugun> gugunList = new ArrayList<>();
        Gugun gangnam = new Gugun(5, "gangnam", 1);
        Gugun jeonju = new Gugun(12, "jeonju", 5);
        gugunList.add(gangnam);
        gugunList.add(jeonju);
        List<Gugun> seoulGugunList = new ArrayList<>();
        for (Gugun gugun : gugunList) {
            if (gugun.sidoCode == seoul.code) {
                seoulGugunList.add(gugun);
            }
        }
        assertThat(seoulGugunList.size()).isEqualTo(1);
        assertThat(seoulGugunList.get(0).name).isEqualTo("gangnam");
    }

    @Test
    @DisplayName("구군에 속한 전체 여행지 조회")
    void 구군에_속한_모든_여행지를_조회한다() {
        Gugun jeonju = new Gugun(12, "jeonju", 5);
        List<Spot> spots = new ArrayList<>();
        Spot hanokVillage = new Spot("Hanok", 12, 1);
        Spot nambuMarket = new Spot("Nambu", 12, 2);
        Spot Hangang = new Spot("Hangang", 1, 3);
        spots.add(hanokVillage);
        spots.add(nambuMarket);
        spots.add(Hangang);
        List<Spot> jeonjuSpots = new ArrayList<>();
        for (Spot spot : spots) {
            if (spot.gugunCode == jeonju.code) {
                jeonjuSpots.add(spot);
            }
        }
        assertThat(jeonjuSpots.size()).isEqualTo(2);
        assertThat(jeonjuSpots.get(0).gugunCode).isEqualTo(jeonju.code);
    }

    @Test
    @DisplayName("컨텐츠 필터에 해당하는 여행지 조회")
    void 선택한_컨텐츠_종류에_해당하는_모든_여행지를_조회한다() {
        List<Spot> spots = new ArrayList<>();
        Spot hanokVillage = new Spot("Hanok", 12, 1);
        Spot nambuMarket = new Spot("Nambu", 12, 2);
        Spot traditionalMarket = new Spot("tradition", 14, 2);
        spots.add(hanokVillage);
        spots.add(nambuMarket);
        spots.add(traditionalMarket);

        Content market = new Content(2, "market");
        List<Spot> marketSpots = new ArrayList<>();
        for (Spot spot : spots) {
            if(spot.contentCode==market.code){
                marketSpots.add(spot);
            }
        }
        assertThat(marketSpots.size()).isEqualTo(2);
        assertThat(marketSpots.get(0).contentCode).isEqualTo(market.code);
    }


    static class Sido {
        int code;
        String name;

        public Sido(int code, String name) {
            this.code = code;
            this.name = name;
        }
    }

    static class Gugun {
        int code;
        String name;
        int sidoCode;

        public Gugun(int code, String name, int sidoCode) {
            this.code = code;
            this.name = name;
            this.sidoCode = sidoCode;
        }
    }

    static class Spot {
        String name;
        int gugunCode;
        int contentCode;

        public Spot(String name, int gugunCode, int contentCode) {
            this.name = name;
            this.gugunCode = gugunCode;
            this.contentCode = contentCode;
        }
    }

    static class Content{
        int code;
        String name;

        public Content(int code, String name) {
            this.code = code;
            this.name = name;
        }
    }
}
