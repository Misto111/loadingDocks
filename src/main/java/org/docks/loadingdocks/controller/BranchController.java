package org.docks.loadingdocks.controller;

import org.docks.loadingdocks.enums.BranchLocationEnum;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BranchController {


    @GetMapping("/api/branches")
    public List<BranchLocation> getBranches() {
        return List.of(BranchLocationEnum.values()).stream()
                .map(branch -> new BranchLocation(branch.getName(), branch.getLat(), branch.getLng(), branch.getInfoUrl()))
                .collect(Collectors.toList());
    }

    public static class BranchLocation {
        private String name;
        private double lat;
        private double lng;
        private String infoUrl;

        public BranchLocation(String name, double lat, double lng, String infoUrl) {
            this.name = name;
            this.lat = lat;
            this.lng = lng;
            this.infoUrl = infoUrl;
        }

        public String getName() {
            return name;
        }

        public double getLat() {
            return lat;
        }

        public double getLng() {
            return lng;
        }

        public String getInfoUrl() {
            return infoUrl;
        }
    }
}