<template>
  <div class="app-container">
    <WorkOrderAudit :orderType="orderType"
                    :adoptPermission="adoptPermission"
                    :rejectPermission="rejectPermission"
    ></WorkOrderAudit>
  </div>
</template>

<script>
import WorkOrderAudit from "@/components/work/workOrderAudit";

export default {
  components: {
    WorkOrderAudit
  },
  data() {
    return {
      orderType: "3",
      adoptPermission: "work:sanitation:cleanersAudit:adopt",
      rejectPermission: "work:sanitation:cleanersAudit:reject"
    };
  }
};
</script>
<style scoped>
</style>
