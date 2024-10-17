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
      // 工单类型
      orderType: "1",
      adoptPermission: "nightPatrol:audit:adopt",
      rejectPermission: "nightPatrol:audit:reject"
    };
  }
};
</script>
<style scoped>
</style>
